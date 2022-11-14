package de.playground.k8s;
// Import classes:

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.LinkedTreeMap;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileReader;
import java.io.IOException;

// kubectl get ingressroutes  -o yaml
public class GetCustomResources {

    public static void main(String[] args) throws IOException {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost");

        // Configure API key authorization: BearerToken
        String kubeConfigPath = System.getenv("HOME") + "/.kube/config-cluster-dev";
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
//        Configuration.setDefaultApiClient(client);
//        CoreV1Api api = new CoreV1Api();
        //better:


        CustomObjectsApi apiInstance = new CustomObjectsApi(client);
        String group = "traefik.containo.us"; // String | the custom resource's group
        String version = "v1alpha1"; // String | the custom resource's version
        String plural = "ingressroutes"; // String | the custom object's plural name. For TPRs this would be lowercase plural kind.
        String name = ""; // String | the custom object's name
        try {
            LinkedTreeMap result = (LinkedTreeMap) apiInstance.getClusterCustomObject(group, version, plural, name);

            final JsonFactory factory = new JsonFactory();
            final ObjectMapper mapper = new ObjectMapper(factory);


            //create JsonNode from json String
            final JsonNode rootNode = mapper.valueToTree(result);
            ;
            System.out.println(rootNode.toPrettyString());
            System.out.println("---------------------");
            JsonNode items = rootNode.get("items");


            for (JsonNode item : items) {
                JsonNode spec = item.get("spec");
                JsonNode namespace = item.get("metadata").get("namespace");
                if (namespace.toString().contains("webapps") || namespace.toString().contains("default")) {
                    System.out.println("namespace:" + namespace);
                    if (spec.get("tls") != null) {
                        JsonNode tls = spec.get("tls");
                        JsonNode domains = tls.get("domains");
                        for (JsonNode domain : domains) {
                            System.out.println("domain:" + domain);
                        }
                    }
                }
            }

        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#getClusterCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
