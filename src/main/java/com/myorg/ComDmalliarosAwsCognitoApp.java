package com.myorg;

import software.amazon.awscdk.core.App;

public final class ComDmalliarosAwsCognitoApp {
    public static void main(final String[] args) {
        App app = new App();

        new ComDmalliarosAwsCognitoStack(app, "ComDmalliarosAwsCognitoStack");

        app.synth();
    }
}
