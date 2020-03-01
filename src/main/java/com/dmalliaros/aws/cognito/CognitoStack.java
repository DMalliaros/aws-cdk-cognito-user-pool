package com.dmalliaros.aws.cognito;

import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.cognito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

public class CognitoStack extends Stack {

    public CognitoStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);
        final String env = System.getenv("ENVIRONMENT") == null ? "demo" : System.getenv("ENVIRONMENT");
        final String cognitoDomain = System.getenv("COGNITO_DOMAIN") == null ? env + "user-pool-domain-123" : System.getenv("COGNITO_DOMAIN");

        final Map<String, String> tas = new HashMap<>();
        tas.put("environment", env);

        tas.forEach((key, value) -> {
            Tag.add(parent, key, value);
        });

        final CfnUserPool cfnUserPool = new CfnUserPool(this, id + "userPool", CfnUserPoolProps
                .builder()
                .adminCreateUserConfig(CfnUserPool.AdminCreateUserConfigProperty
                        .builder()
                        .allowAdminCreateUserOnly(true)
                        .build())
                .userPoolTags(tas)
                .usernameAttributes(singletonList("email"))
                .mfaConfiguration("OFF")
                .userPoolName(env + "-user-pool")
                .policies(CfnUserPool.PoliciesProperty
                        .builder()
                        .passwordPolicy(CfnUserPool.PasswordPolicyProperty
                                .builder()
                                .minimumLength(6)
                                .requireLowercase(false)
                                .requireNumbers(false)
                                .requireSymbols(false)
                                .requireUppercase(false)
                                .build())
                        .build())
                .build());

        final CfnUserPoolClient cfnUserPoolClient = new CfnUserPoolClient(this, id + "userPoolClient", CfnUserPoolClientProps
                .builder()
                .generateSecret(false)
                .clientName(env + "userPoolClient")
                .userPoolId(cfnUserPool.getRef())
                .callbackUrLs(singletonList("http://localhost:3000/"))
                .logoutUrLs(singletonList("http://localhost:3000/"))
                .supportedIdentityProviders(singletonList("COGNITO"))
                .allowedOAuthFlows(singletonList("implicit"))
                .allowedOAuthScopes(Arrays.asList("aws.cognito.signin.user.admin", "email", "openid"))
                .build());
        final CfnUserPoolDomain cfnUserPoolDomain = new CfnUserPoolDomain(this, id + "userPoolDomain", CfnUserPoolDomainProps
                .builder()
                .domain(cognitoDomain)
                .userPoolId(cfnUserPool.getRef())
                .build());

        new CfnOutput(this, id + "OutputUserPoolId", CfnOutputProps
                .builder()
                .description("This is the user pool id")
                .exportName("UserPoolId")
                .value(cfnUserPool.getRef())
                .build());
        new CfnOutput(this, id + "OutputUserPoolURLRPrefix", CfnOutputProps
                .builder()
                .description("This is the user pool url Prefix")
                .exportName("UserPoolURLPrefix")
                .value(cfnUserPoolDomain.getDomain())
                .build());
        new CfnOutput(this, id + "OutputUserPoolURL", CfnOutputProps
                .builder()
                .description("This is the user pool url")
                .exportName("UserPoolURL")
                .value("https://" + cfnUserPoolDomain.getDomain() + ".auth." + props.getEnv().getRegion() + ".amazoncognito.com")
                .build());
        new CfnOutput(this, id + "OutputUserPoolClientId", CfnOutputProps
                .builder()
                .description("This is the user pool Client id")
                .exportName("UserPoolClientId")
                .value(cfnUserPoolClient.getRef())
                .build());

    }
}
