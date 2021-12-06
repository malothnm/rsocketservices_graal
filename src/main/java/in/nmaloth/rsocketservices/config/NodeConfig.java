package in.nmaloth.rsocketservices.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.nmaloth.rsocketservices.config.model.NodeDetails;
import in.nmaloth.rsocketservices.config.model.NodeInfo;
import in.nmaloth.rsocketservices.constants.ResourceConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@Slf4j
public class NodeConfig {

    private final Environment environment;
    private final ResourceLoader resourceLoader;


    public NodeConfig(Environment environment, ResourceLoader resourceLoader) {
        this.environment = environment;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public NodeInfo nodeInfo(){


        String appName = environment.getProperty("spring.application.name");
        String instanceName = environment.getProperty("spring.instance.name");

        log.info("############### instance Name : {}" ,instanceName);


       if(appName == null){
           throw new RuntimeException("###..Application Name is Mandatory..##");
       }

        ObjectMapper objectMapper = new ObjectMapper();
        List<NodeDetails> nodeDetailsList = getDependentNodeInfo(objectMapper);

        NodeDetails nodeDetails = null;
        for(String activeProfile: environment.getActiveProfiles()){
            Optional<NodeDetails> nodeDetailsOptional = findActiveNode(activeProfile,nodeDetailsList);
            if(nodeDetailsOptional.isPresent()){
                nodeDetails = nodeDetailsOptional.get();
                break;
            }
        }
        if(nodeDetails == null){
            throw  new RuntimeException(" No Active profile for dependent Node");
        }

        NodeInfo.NodeInfoBuilder builder = NodeInfo.builder()
                .instanceName(instanceName)
                .dependentService(nodeDetails.getDependentService())
                .appName(appName);

        if(nodeDetails.getServerPort() != 0){
            builder.serverPort(nodeDetails.getServerPort());
        }

        return builder.build();

    }




    private List<NodeDetails> getDependentNodeInfo(ObjectMapper objectMapper){

        List<NodeDetails> nodeDetailsList = new ArrayList<>();

        Resource resource = resourceLoader.getResource(ResourceConstants.DEPENDENT_YML);

        try (InputStream inputStream = resource.getInputStream()){
            Yaml yaml = new Yaml(new SafeConstructor());
            yaml.loadAll(inputStream)
                    .forEach(o ->{
                        NodeDetails nodeDetails = objectMapper.convertValue(o,NodeDetails.class);
                        nodeDetailsList.add(nodeDetails);
                    });
        } catch ( Exception ex){
            ex.printStackTrace();
        }
        return nodeDetailsList;

    }


    private Optional<NodeDetails> findActiveNode(String activeProfile, List<NodeDetails> nodeDetailsList){

        return nodeDetailsList.stream()
                .filter(nodeDetails -> nodeDetails.getProfile().equals(activeProfile))
                .findFirst();

    }


}
