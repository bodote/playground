package de.playground.k8s;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.Watch;
import okhttp3.OkHttpClient;

import java.io.*;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WatchCustomResource {
  public static void main(String[] args) throws IOException, ApiException {
    String kubeConfigPath = System.getenv("HOME") + "/.kube/config-cluster-dev";
    ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

    // infinite timeout
    OkHttpClient httpClient = client.getHttpClient().newBuilder().readTimeout(0, TimeUnit.SECONDS).build();
    client.setHttpClient(httpClient);

    // Configure API key authorization: BearerToken

    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //BearerToken.setApiKeyPrefix("Token");

    CustomObjectsApi apiInstance = new CustomObjectsApi(client);
    String group = "traefik.containo.us"; // String | The custom resource's group name
    String version = "v1alpha1"; // String | The custom resource's version
    String plural = "ingressroutes"; // String | The custom resource's plural name. For TPRs this would be lowercase
    // plural kind.
    String pretty = ""; // String | If 'true', then the output is pretty printed.
    Boolean allowWatchBookmarks = true; // Boolean | allowWatchBookmarks requests watch events with type \"BOOKMARK\"
    // . Servers that do not implement bookmarks may ignore this flag and bookmarks are sent at the server's
    // discretion. Clients should not assume bookmarks are returned at any specific interval, nor may they assume the
    // server will send any BOOKMARK event during a session. If this is not a watch, this field is ignored. If the
    // feature gate WatchBookmarks is not enabled in apiserver, this field is ignored.
    String _continue = ""; // String | The continue option should be set when retrieving more
    // results from the server. Since this value is server defined, clients may only use the continue value from a
    // previous query result with identical query parameters (except for the value of continue) and the server may
    // reject a continue value it does not recognize. If the specified continue value is no longer valid whether due
    // to expiration (generally five to fifteen minutes) or a configuration change on the server, the server will
    // respond with a 410 ResourceExpired error together with a continue token. If the client needs a consistent
    // list, it must restart their list without the continue field. Otherwise, the client may send another list
    // request with the token received with the 410 error, the server will respond with a list starting from the next
    // key, but from the latest snapshot, which is inconsistent from the previous list results - objects that are
    // created, modified, or deleted after the first list request will be included in the response, as long as their
    // keys are after the \"next key\".  This field is not supported when watch is true. Clients may start a watch
    // from the last resourceVersion value returned by the server and not miss any modifications.
    String fieldSelector = ""; // String | A selector to restrict the list of returned objects
    // by their fields. Defaults to everything.
    String labelSelector = ""; // String | A selector to restrict the list of returned objects
    // by their labels. Defaults to everything.
    Integer limit = null; // Integer | limit is a maximum number of responses to return for a list call. If more items
    // exist, the server will set the `continue` field on the list metadata to a value that can be used with the same
    // initial query to retrieve the next set of results. Setting a limit may return fewer than the requested amount
    // of items (up to zero items) in the event all requested objects are filtered out and clients should only use
    // the presence of the continue field to determine whether more results are available. Servers may choose not to
    // support the limit argument and will return all of the available results. If limit is specified and the
    // continue field is empty, clients may assume that no more results are available. This field is not supported if
    // watch is true.  The server guarantees that the objects returned when using continue will be identical to
    // issuing a single list call without a limit - that is, no objects created, modified, or deleted after the first
    // request is issued will be included in any subsequent continued requests. This is sometimes referred to as a
    // consistent snapshot, and ensures that a client that is using limit to receive smaller chunks of a very large
    // result can ensure they see all possible objects. If objects are updated during a chunked list the version of
    // the object that was present at the time the first list result was calculated is returned.
    String resourceVersion = null; // String | When specified with a watch call, shows changes
    // that occur after that particular version of a resource. Defaults to changes from the beginning of history.
    // When specified for list: - if unset, then the result is returned from remote storage based on quorum-read
    // flag; - if it's 0, then we simply return what we currently have in cache, no guarantee; - if set to non zero,
    // then the result is at least as fresh as given rv.
    String resourceVersionMatch = ""; // String | resourceVersionMatch determines how resourceVersion
    // is applied to list calls. It is highly recommended that resourceVersionMatch be set for list calls where
    // resourceVersion is
    // set See https://kubernetes.io/docs/reference/using-api/api-concepts/#resource-versions
    // for details.  Defaults to unset
    Integer timeoutSeconds = 6; // Integer | Timeout for the list/watch call. This limits the duration of the call,
    // regardless of any activity or inactivity.
    Boolean watchFlag = true; // Boolean | Watch for changes to the described resources and return them as a stream of
    // add, update, and remove notifications.


    final ObjectMapper mapper = new ObjectMapper(new JsonFactory());
    String filePath = "backend/src/test/resources/";
    JsonNode rootNode = null;
    while (resourceVersion == null ) {
      try {
        LinkedTreeMap result = (LinkedTreeMap) apiInstance.listClusterCustomObject(group, version, plural, "false", allowWatchBookmarks, _continue, fieldSelector, labelSelector, limit, resourceVersion, resourceVersionMatch, timeoutSeconds, false);
        String filename = filePath+"initialLinkedTreeMap.json";
        writeTestFile(result, filename,mapper);
        JsonNode result2 = readTestFile(filename,mapper);
        resourceVersion = extractDomainsFromJson(result2);
      } catch (Exception e) {
        System.err.println("Exception when calling CustomObjectsApi#listClusterCustomObject");
        e.printStackTrace();
        try {
          Thread.sleep(5000);
        } catch (InterruptedException ex) {
          throw new RuntimeException(ex);
        }
      }
    }
    //create JsonNode from json String
    Watch.Response<Object> previouseEvent =null;
    while (true) {
      System.out.println("--------START/WHILE-------------");
      try (Watch<Object> watch = Watch.createWatch(client, apiInstance.listClusterCustomObjectCall(group, version,
              plural, pretty, allowWatchBookmarks, _continue, fieldSelector, labelSelector, limit, resourceVersion,
              resourceVersionMatch, timeoutSeconds, watchFlag, null), new TypeToken<Watch.Response<Object>>() {
      }.getType())) {
        for (Watch.Response<Object> event : watch) {
          System.out.printf("+++++" + new Date() + "\n");
          String filename = filePath+"event"+event.type+".json";
          writeEventTestFile(event, filename,mapper);
          JsonNode eventJson = readEventTestFile(filename,mapper);
          resourceVersion = extractChangedDomainsFromJson(eventJson);
          if (resourceVersion==null) {
            JsonNode unknown = mapper.valueToTree(event);
            System.out.println("no metadata, instead:"+unknown.toPrettyString());
          }
          System.out.println("-----END WATCH FOR LOOP----------------");
        }
        System.out.println("--------END WATCH CALL-------------");
      } catch (ApiException e) {
        System.err.println("Exception when calling CustomObjectsApi#listClusterCustomObject");
        System.err.println("Status code: " + e.getCode());
        System.err.println("Reason: " + e.getResponseBody());
        System.err.println("Response headers: " + e.getResponseHeaders());
        e.printStackTrace();
        try {
          Thread.sleep(5000);
        } catch (InterruptedException ex) {
          throw new RuntimeException(ex);
        }
      } catch (RuntimeException | ClassNotFoundException e ){
        System.err.println("Exception when calling CustomObjectsApi#listClusterCustomObject");
        e.printStackTrace();
        try {
          Thread.sleep(5000);
        } catch (InterruptedException ex) {
          throw new RuntimeException(ex);
        }
      }
    }
  }

  private static JsonNode readTestFile(String filename,ObjectMapper mapper) throws IOException,
          ClassNotFoundException {
    JsonNode result;
    result = mapper.readValue(Paths.get(filename).toFile(), JsonNode.class);
    return result;
  }

  private static void writeTestFile(LinkedTreeMap result, String filename,ObjectMapper mapper) throws IOException {

    mapper.writeValue(Paths.get(filename).toFile(), result);
  }

  private static JsonNode readEventTestFile(String filename,ObjectMapper mapper) throws IOException,
          ClassNotFoundException {
   JsonNode result;
    result = mapper.readValue(Paths.get(filename).toFile(), JsonNode.class);
    return result;
  }

  private static void writeEventTestFile(Watch.Response<Object> event,String filename,ObjectMapper mapper) throws IOException {
    mapper.writeValue(Paths.get(filename).toFile(), event);
  }
  private static String extractDomainsFromJson(JsonNode rootNode){
    System.out.println("resourceVersion:" + rootNode.get("metadata").get("resourceVersion"));
    String resourceVersion = rootNode.get("metadata").get("resourceVersion").asText();
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
        } else if (spec.get("routes") != null) {
          JsonNode routes = spec.get("routes");
          System.out.println("route:match:" + routes.get(0).get("match").asText());
        }
      }
    }
    return resourceVersion;
  }
  private static String  extractChangedDomainsFromJson(JsonNode eventJson){
    JsonNode rootNode = eventJson.get("object");
    JsonNode spec = rootNode.get("spec");
    if (spec != null ) {
      JsonNode tls = (spec.get("tls") );
      if (tls != null) {
        for (JsonNode dom : tls.get("domains")) {
          System.out.println("domain:main:" + dom.get("main").toPrettyString());
        }
      } else if (spec.get("routes") != null) {
        JsonNode routes = spec.get("routes");
        System.out.println("route:match:" + routes.get(0).get("match").asText());
      }
    }
    JsonNode meta = rootNode.get("metadata");
    String resourceVersion=null;
    if (meta!=null) {
      resourceVersion = rootNode.get("metadata").get("resourceVersion").asText();
      System.out.println("resourceVersion:" + resourceVersion);
    }
    String eventType = eventJson.get("type").asText();
    switch (eventType) {
      case "BOOKMARK":
        System.out.println("----BOOKMARK------");
        break;
      case "ADDED":
        System.out.printf("----ADD------ %s%n", eventType);
        break;
      case "MODIFIED":
      case "DELETED":
        System.out.printf("----MOD/DEL------ %s%n", eventType);
        break;
      default:
        System.out.printf("Unknown event type: %s%n", eventType);
    }
    return resourceVersion;
  }
}
