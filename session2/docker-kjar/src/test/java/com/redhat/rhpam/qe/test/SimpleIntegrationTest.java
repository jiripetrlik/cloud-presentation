package com.redhat.rhpam.qe.test;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;

public class SimpleIntegrationTest {

    private static final String KIE_SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";
    private static final String KIE_SERVER_USER = "user";
    private static final String KIE_SERVER_PASSWORD = "test";

    private static final String CONTAINER_ID = "test-container";
    private static final String PROCESS_NAME = "process1";

    @Test
    public void testKjar() {
        KieServicesConfiguration kieServicesConfiguration =
                KieServicesFactory.newRestConfiguration(KIE_SERVER_URL, KIE_SERVER_USER, KIE_SERVER_PASSWORD);
        KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfiguration);
        ServiceResponse<KieServerInfo> serverInfo = kieServicesClient.getServerInfo();
        Assertions.assertThat(serverInfo).isNotNull();

        final QueryServicesClient queryServicesClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
        final List<ProcessDefinition> processes = queryServicesClient.findProcesses(0, 100);
        Assertions.assertThat(processes).isNotNull();
        Assertions.assertThat(processes.size() > 0).isTrue();

        final ProcessServicesClient processServicesClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        Long instanceId = processServicesClient.startProcess(CONTAINER_ID, PROCESS_NAME);
        Assertions.assertThat(instanceId).isNotNull();
        Assertions.assertThat(instanceId > 0).isTrue();
    }
}
