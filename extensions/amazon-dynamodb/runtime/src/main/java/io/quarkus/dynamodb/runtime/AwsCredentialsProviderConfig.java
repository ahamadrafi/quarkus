package io.quarkus.dynamodb.runtime;

import java.time.Duration;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

@ConfigGroup
public class AwsCredentialsProviderConfig {

    // @formatter:off
    /**
     * Configure the credentials provider that should be used to authenticate with AWS.
     *
     * Available values:
     *
     * * `DEFAULT` - the provider will attempt to identify the credentials automatically using the following checks:
     * ** Java System Properties - `aws.accessKeyId` and `aws.secretKey`
     * ** Environment Variables - `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`
     * ** Credential profiles file at the default location (`~/.aws/credentials`) shared by all AWS SDKs and the AWS CLI
     * ** Credentials delivered through the Amazon EC2 container service if `AWS_CONTAINER_CREDENTIALS_RELATIVE_URI` environment variable is set and security manager has permission to access the variable.
     * ** Instance profile credentials delivered through the Amazon EC2 metadata service
     * * `STATIC` - the provider that uses the access key and secret access key specified in the `tatic-provider` section of the config.
     * * `SYSTEM_PROPERTY` - it loads credentials from the `aws.accessKeyId`, `aws.secretAccessKey` and `aws.sessionToken` system properties.
     * * `ENV_VARIABLE` - it loads credentials from the `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY` and `AWS_SESSION_TOKEN` environment variables.
     * * `PROFILE` - credentials are based on AWS configuration profiles. This loads credentials from
     *               a http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html[profile file],
     *               allowing you to share multiple sets of AWS security credentials between different tools like the AWS SDK for Java and the AWS CLI.
     * * `CONTAINER` - It loads credentials from a local metadata service. Containers currently supported by the AWS SDK are
     *                 **Amazon Elastic Container Service (ECS)** and **AWS Greengrass**
     * * `INSTANCE_PROFILE` - It loads credentials from the Amazon EC2 Instance Metadata Service.
     * * `PROCESS` - Credentials are loaded from an external process. This is used to support the credential_process setting in the profile
     *               credentials file. See https://docs.aws.amazon.com/cli/latest/topic/config-vars.html#sourcing-credentials-from-external-processes[Sourcing Credentials From External Processes]
     *               for more information.
     * * `ANONYMOUS` - It always returns anonymous AWS credentials. Anonymous AWS credentials result in un-authenticated requests and will
     *                 fail unless the resource or API's policy has been configured to specifically allow anonymous access.
     *
     * @asciidoclet
     */
    // @formatter:on
    @ConfigItem(defaultValue = "DEFAULT")
    public AwsCredentialsProviderType type;

    /**
     * Default credentials provider configuration
     */
    @ConfigItem
    public DefaultCredentialsProviderConfig defaultProvider;

    /**
     * Static credentials provider configuration
     */
    @ConfigItem
    public StaticCredentialsProviderConfig staticProvider;

    /**
     * AWS Profile credentials provider configuration
     */
    @ConfigItem
    public ProfileCredentialsProviderConfig profileProvider;

    /**
     * Process credentials provider configuration
     */
    @ConfigItem
    public ProcessCredentialsProviderConfig processProvider;

    @ConfigGroup
    public static class DefaultCredentialsProviderConfig {

        /**
         * Configure whether this provider should fetch credentials asynchronously in the background.
         *
         * <p>
         * If this is `true`, threads are less likely to block, but additional resources are used to maintain the provider.
         */
        @ConfigItem
        public boolean asyncCredentialUpdateEnabled;

        /**
         * Controls whether the provider should reuse the last successful credentials provider in the chain.
         *
         * <p>
         * Reusing the last successful credentials provider will typically return credentials faster than searching through the
         * chain.
         */
        @ConfigItem(defaultValue = "true")
        public boolean reuseLastProviderEnabled;
    }

    @ConfigGroup
    public static class StaticCredentialsProviderConfig {
        /**
         * AWS Access key id
         */
        @ConfigItem
        public String accessKeyId;

        /**
         * AWS Secret access key
         */
        @ConfigItem
        public String secretAccessKey;
    }

    @ConfigGroup
    public static class ProfileCredentialsProviderConfig {
        /**
         * Define the name of the profile that should be used by this credentials provider.
         *
         * <p>
         * If not specified, the value in `AWS_PROFILE` environment variable or `aws.profile` system property is used and
         * defaults to `default` name.
         */
        @ConfigItem
        public Optional<String> profileName;
    }

    @ConfigGroup
    public static class ProcessCredentialsProviderConfig {
        /**
         * Configure whether the provider should fetch credentials asynchronously in the background.
         *
         * <p>
         * If this is true, threads are less likely to block when credentials are loaded, but additional resources are used to
         * maintain the provider.
         * <p>
         * By default, this is disabled.
         */
        @ConfigItem
        public boolean asyncCredentialUpdateEnabled;

        /**
         * Configure the amount of time between when the credentials expire and when the credentials should start to be
         * refreshed.
         *
         * <p>
         * This allows the credentials to be refreshed *before* they are reported to expire.
         * <p>
         * Default: 15 seconds.
         */
        @ConfigItem
        public Optional<Duration> credentialRefreshThreshold;

        /**
         * Configure the maximum amount of `bytes` that can be returned by the external process before an exception is raised.
         *
         * <p>
         * Default: 1024 bytes.
         */
        @ConfigItem
        public Optional<Integer> processOutputLimit;

        /**
         * The command that should be executed to retrieve credentials.
         */
        @ConfigItem
        public String command;
    }
}
