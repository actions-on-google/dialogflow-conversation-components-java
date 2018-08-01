# Actions on Google: Conversation Component Sample

A simple sample showing the visual conversation components available with Actions on Google.

## Setup Instructions

### Webhook
The boilerplate includes entry points for both Google App Engine and AWS Lambda.

#### Build for Google Cloud Platform
    1. Delete ActionsAWSHandler.java
    1. Remove the following line from build.gradle:
       1. `apply from: 'build-aws.gradle'`
    1. Download the [SDK for App Engine](https://cloud.google.com/appengine/docs/flexible/java/download)
    1. Follow the steps for [Setting up a GCP project](https://cloud.google.com/appengine/docs/flexible/java/using-gradle#setting_up_and_validating_your_project_name_short)
    1. Deploy to [App Engine using Gradle](https://cloud.google.com/appengine/docs/flexible/java/using-gradle) by running the following command: `gradle appengineDeploy`

#### Build for AWS
    1. Delete ActionsServlet
    1. Remove the following line from build.gradle:
       1. `apply from: 'build-gcp.gradle'`
    1. Build the AWS Lambda compatible zip file using the buildAWSZip gradle task: `gradle buildAWSZip`
    1. Deploy the zip file found at `build/distributions/myactions.zip` as an AWS Lambda function by following instructions at https://aws.amazon.com/lambda/

### Action configuration
1. Use the [Actions on Google Console](https://console.actions.google.com) to add a new project with a name of your choosing and click *Create Project*.
1. Click *Skip*, located on the top right.
1. On the left navigation menu under *BUILD*, click on *Actions*. Click on *Add Your First Action* and choose your app's language(s).
1. Select *Custom intent*, click *BUILD*. This will open a Dialogflow console. Click *CREATE*.
1. Click on the gear icon to see the project settings.
1. Select *Export and Import*.
1. Select *Restore from zip*. Follow the directions to restore from the `agent.zip` file in this repo.
1. Deploy the fulfillment webhook as described in the previous section
1. Go back to the Dialogflow console and select *Fulfillment* from the left navigation menu. Enable *Webhook*, set the value of *URL* to the webhook from the previous section, then click *Save*.


### Test on the Actions on Google simulator
1. Select [*Integrations*](https://console.dialogflow.com/api-client/#/agent//integrations) from the left navigation menu and open the *Settings* menu for Actions on Google.
1. Enable *Auto-preview changes* and Click *Test*. This will open the Actions on Google simulator.
1. Type `Talk to my test app` in the simulator, or say `OK Google, talk to my test app` to any Actions on Google enabled device signed into your developer account.

For more detailed information on deployment, see the [documentation](https://developers.google.com/actions/dialogflow/deploy-fulfillment).

## References and How to report bugs
* Actions on Google documentation: [https://developers.google.com/actions/](https://developers.google.com/actions/).
* If you find any issues, please open a bug here on GitHub.
* Questions are answered on [StackOverflow](https://stackoverflow.com/questions/tagged/actions-on-google).

## How to make contributions?
Please read and follow the steps in the [CONTRIBUTING.md](CONTRIBUTING.md).

## License
See [LICENSE](LICENSE).

## Terms
Your use of this sample is subject to, and by using or downloading the sample files you agree to comply with, the [Google APIs Terms of Service](https://developers.google.com/terms/).

## Google+
Actions on Google Developers Community on Google+ [https://g.co/actionsdev](https://g.co/actionsdev).
