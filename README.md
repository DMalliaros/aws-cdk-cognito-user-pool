# Amazon CDK and Cognito User Pool

This repository contains an example projects for [AWS Cloud Development Kit](https://docs.aws.amazon.com/cdk/latest/guide/home.html) or known as CDK and a [Cognito User Pool](https://docs.aws.amazon.com/cognito/latest/developerguide/cognito-user-identity-pools.html).
We will simple configure an Cognito User pool.

## Tools

* AWS CDK (Java)
* Node (>= 10.3.0)

## Cognito

Cognito can manage the users. That can sign in from different identity providers  like Google, Facebook, Amazon and through SAML identity providers. Can handle the full life circle of a user management.
On this demo we create a very simple Cognito User Pool

### Info

* Cognito User Pool simple configuration 
* Cognito User Pool put Domain
* Cognito User Pool Client simple configuration 


## CDK

### Install
To install CDK [check the link](https://docs.aws.amazon.com/cdk/latest/guide/getting_started.html)

```shell script
npm install -g aws-cdk
```

### Deploy
Properties that need:

* CDK_DEFAULT_ACCOUNT
* CDK_DEFAULT_REGION

Optional properties can pass:

* ENVIRONMENT = demo
* COGNITO_DOMAIN =  ENVIRONMENT + "user-pool-domain-123"

```shell script
mvn compile -DCDK_DEFAULT_ACCOUNT=12312313 -DCDK_DEFAULT_REGION=eu-west-1
cdk deploy
```

### Destroy

Drop the stack

```shell script
cdk destroy
```