package de.playground.k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileReader;
import java.io.IOException;

public class GetNodeList {
    public static void main(String[] args) throws IOException, ApiException{
        // file path to your KubeConfig

        String kubeConfigPath = System.getenv("HOME") + "/.kube/config-cluster-dev";
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
//        Configuration.setDefaultApiClient(client);
//        CoreV1Api api = new CoreV1Api();
        //better:
        CoreV1Api api = new CoreV1Api(client);
        listNodes( api);
        listPodAllNS(api);
    }
    private static void listNodes(CoreV1Api api) throws ApiException {
        System.out.println("--------listNodes:\n");
        V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, 10, false);
        nodeList.getItems()
                .stream()
                .forEach((node) -> System.out.println(node));
        System.out.println("---------ENDE listNodes:\n");
    }
    private static void listPodAllNS(CoreV1Api api) throws ApiException {
        System.out.println("--------listPodAllNS:\n");
        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null,false);
        for (V1Pod item : list.getItems()) {
            if (item.getMetadata().getNamespace().contains("webapps")){
                System.out.println(item.getMetadata().getName()+ " NS:"+item.getMetadata().getNamespace());
            }

        }
        System.out.println("---------ENDE listPodAllNS:\n");
    }
}