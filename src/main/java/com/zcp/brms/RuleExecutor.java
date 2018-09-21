package com.zcp.brms;

import com.myspace.zcp.CreditRequest;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieServiceResponse;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.client.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RuleExecutor {
    private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
    private static final String USER = "wbadmin";
    private static final String PASSWORD = "wbadmin";

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    private KieServicesConfiguration conf;
    private KieServicesClient kieServicesClient;

    public void initialize() {
        conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
        conf.setMarshallingFormat(FORMAT);
        kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
    }

    public void executeCommands() {
        String containerId = "ZCP_1.0.2";
        System.out.println("== Sending commands to the server ==");
        RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

        CreditRequest zcpCreditRequest = new CreditRequest();
        zcpCreditRequest.setBIN("020016");
        zcpCreditRequest.setReleaseNumber("D1");
        zcpCreditRequest.setActions(new ArrayList<>());

        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        Command<?> insert = commandsFactory.newInsert(zcpCreditRequest);
        Command<?> fireAllRules = commandsFactory.newFireAllRules();
        Command<?> batchCommand = commandsFactory.newBatchExecution(Arrays.asList(insert, fireAllRules));
        ServiceResponse<ExecutionResults> executeResponse = rulesClient.executeCommandsWithResults(containerId, batchCommand);
        if(executeResponse.getType() == KieServiceResponse.ResponseType.SUCCESS) {
            System.out.println("Commands executed with success! Response: ");
            System.out.println(executeResponse.getResult());
        }
        else {
            System.out.println("Error executing rules. Message: ");
            System.out.println(executeResponse.getMsg());
        }
    }

    public void listProcesses() {
        System.out.println("== Listing Business Processes ==");
        QueryServicesClient queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
        List<ProcessDefinition> findProcessesByContainerId = queryClient.findProcessesByContainerId("rewards", 0, 1000);
        for (ProcessDefinition def : findProcessesByContainerId) {
            System.out.println(def.getName() + " - " + def.getId() + " v" + def.getVersion());
        }
    }

    public static void main(String[] args) {
        RuleExecutor ex = new RuleExecutor();
        ex.initialize();
        ex.executeCommands();
    }
}
