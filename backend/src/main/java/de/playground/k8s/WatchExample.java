package de.playground.k8s;


import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.Watch;
import okhttp3.OkHttpClient;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A simple example of how to use Watch API to watch changes in Namespace list.
 */
public class WatchExample {
    public static void main(String[] args) throws IOException, ApiException {
        String kubeConfigPath = System.getenv("HOME") + "/.kube/config-cluster-dev";
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

        // infinite timeout
        OkHttpClient httpClient =
                client.getHttpClient().newBuilder().readTimeout(0, TimeUnit.SECONDS).build();
        client.setHttpClient(httpClient);
//        Configuration.setDefaultApiClient(client);
//        CoreV1Api api = new CoreV1Api();

        CoreV1Api api = new CoreV1Api(client);
        watchPodsWebappsNS(client, api);
        //watchNameSpaces(client, api);


    }

    private static void watchPodsWebappsNS(ApiClient client, CoreV1Api api) {
        String resourceVersion = null;
        while (true) {
            try (Watch<V1Pod> watch = Watch.createWatch(
                    client,
                    api.listPodForAllNamespacesCall(true, null, null, null,
                            null, "false", resourceVersion, null, 50, true, null),
                    new TypeToken<Watch.Response<V1Pod>>() {
                    }.getType())) {

                for (Watch.Response<V1Pod> event : watch) {
                    System.out.printf("+++++" + new Date() + "\n");
                    V1Pod pod = event.object;
                    V1ObjectMeta meta = pod.getMetadata();
                    switch (event.type) {
                        case "BOOKMARK":
                            resourceVersion = meta.getResourceVersion();
                            break;
                        case "ADDED":
                        case "MODIFIED":
                        case "DELETED":
                            if (pod.getMetadata().getName().contains("omega")) {
                                System.out.printf(" type: %s name:%s | %n", event.type, pod.getMetadata().getName());
                                System.out.printf("MetaData.resourceVersion:" + pod.getMetadata().getResourceVersion() + "\n");
                                System.out.printf("Phase:" + pod.getStatus().getPhase() + "\n");
                                if (pod.getStatus().getContainerStatuses() != null) {
                                    pod.getStatus().getContainerStatuses().forEach(contStatus -> {
                                                System.out.printf("getImageID:" + contStatus.getImageID() + "\n");
                                                System.out.printf("getReady:" + contStatus.getReady());
                                                System.out.printf("  getStarted:" + contStatus.getStarted() + "\n");
                                            }
                                    );
                                }
                                if (pod.getStatus().getConditions() != null) {
                                    pod.getStatus().getConditions().forEach(cond -> System.out.printf("Condition: trTime=" + cond.getLastTransitionTime() +
                                            " type=" + cond.getType() +
                                            " status=" + cond.getStatus()
                                            + "\n"));
                                }

                            }
                            break;
                        default:
                            System.out.printf("Unknown event type: %s", event.type);
                    }
                }
                Date now = new Date();
                System.out.printf("-------" + now + "\n");
            } catch (Exception ex) {
                System.out.printf("\n[E70] ApiError:" + ex.getMessage() + "\n");
            }
        }
    }

    private static void watchNameSpaces(ApiClient client, CoreV1Api api) throws ApiException, IOException {
        Watch<V1Namespace> watch =
                Watch.createWatch(
                        client,
                        api.listNamespaceCall(
                                null, null, null, null, null, 5, null, null, null, Boolean.TRUE, null),
                        new TypeToken<Watch.Response<V1Namespace>>() {
                        }.getType());

        try {
            for (Watch.Response<V1Namespace> item : watch) {
                System.out.printf("%s : %s%n", item.type, item.object.getMetadata().getName());
            }
        } finally {
            watch.close();
            System.exit(0);
        }
    }
}