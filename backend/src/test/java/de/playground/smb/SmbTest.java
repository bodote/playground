package de.playground.smb;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.integration.smb.session.SmbSessionFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
@Slf4j
public class SmbTest {
  String host ="ba-server-048.office.brand-ad.de";
  String transfer="";
  String password ="KuenstlicheAI42!";
  String username ="Bodo.Teichmann";
  @Test
  void integrationSpringtest() throws IOException {
    var ssf = new SmbSessionFactory();
    ssf.setHost(host);
    // ssf.setHost(transfer);
    ssf.setUsername(username);
    ssf.setPassword(password);
    ssf.setShareAndDir("/Transfer/Bodo.Teichmann/");
    var session = ssf.getSession();

    var files = session.list(".");
    for (var file : files) {
      log.info("CanonicalPath {}", file.getCanonicalPath());
    }
    log.info("test.csv {}",session.isFile("test.csv"));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    session.read("test.csv",outputStream);
    byte[] fileContents = outputStream.toByteArray();
    outputStream.close();
    session.close();
    log.info("File content: " + new String(fileContents));
  }

}
