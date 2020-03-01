package com.dmalliaros.aws.cognito;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

import static org.assertj.core.api.Assertions.assertThat;

public class CognitoStackTest {
    private final static ObjectMapper JSON =
            new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    @Test
    public void testStack() {
        App app = new App();
        CognitoStack stack = new CognitoStack(app, "test", StackProps
                .builder()
                .env(Environment
                        .builder()
                        .region("eu-west-1")
                        .account("12356")
                        .build())
                .build());

        JsonNode actual = JSON.valueToTree(app.synth().getStackArtifact(stack.getArtifactId()).getTemplate());
        assertThat(actual.toString()).contains("AWS::Cognito::UserPool");
        assertThat(actual.toString()).contains("AWS::Cognito::UserPoolClient");
    }
}
