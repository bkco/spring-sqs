# sqs-spring

## About
AWS SQS integration with Spring Boot

## Setup and Usage
1.  Create a Standard or Fifo SQS queue in your AWS account. 
2.  Send a plain text message to the queue via: 
    ```
    $ curl -X GET http://localhost:8080/send
    ```
3.  Check that the @SqlListener pulls the message from the SQS queue.

## Nuggets
1.  AWS messaging auto-configuration needs to be excluded.
2.  Messaging currently requires AWS sdkv1; this does not preclude use of AWS sdkv2 for other clients; the two libraries can be included on the same classpath.
3.  SQS is always preferable to HTTP(S) for receiving data from SNS. An SQS queue provides resilience in comparison to a simple stateless HTTP endpoint (in your application).
 


