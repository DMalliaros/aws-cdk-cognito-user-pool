package com.dmalliaros.aws.cognito;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

public final class CognitoApp {
    public static void main(final String[] args) {
        App app = new App();

        new CognitoStack(app, "demo-cognito", StackProps
                .builder()
                .env(Environment
                        .builder()
                        .account(System.getenv("CDK_DEFAULT_ACCOUNT"))
                        .region(System.getenv("CDK_DEFAULT_REGION"))
                        .build())
                .build());

        app.synth();
    }
}
