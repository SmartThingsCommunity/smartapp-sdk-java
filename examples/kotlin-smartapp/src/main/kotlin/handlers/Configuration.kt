package app.handlers

import smartthings.sdk.smartapp.core.Response
import smartthings.sdk.smartapp.core.extensions.ConfigurationHandler
import v1.smartapps.*

class Configuration : ConfigurationHandler {
    override fun handle(request: ExecutionRequest?): ExecutionResponse {
        val response = ConfigurationResponseData.newInstance()
        when (request?.configurationData?.phase) {
            ConfigurationPhase.INITIALIZE -> {
                response.initialize = InitializeSetting()
                    .id("init")
                    .name("Kotlin SmartApp Example")
                    .description("Create a SmartApp using Kotlin!")
                    .firstPageId("intro")
                    .addPermissionsItem("r:devices:*")
                    .addPermissionsItem("x:devices:*")
                return Response.ok(response)
            }
            ConfigurationPhase.PAGE -> {
                when (request.configurationData.pageId) {
                    "intro" -> {
                        response.page = Page()
                            .pageId("intro")
                            .nextPageId("finish")
                            .name("Intro!")
                            .addSectionsItem(Section()
                                .addSettingsItem(DeviceSetting()
                                    .addCapabilitiesItem("switch")
                                    .addPermissionsItem(DeviceSetting.PermissionsEnum.R)
                                    .addPermissionsItem(DeviceSetting.PermissionsEnum.X)
                                    .multiple(true)
                                    .preselect(true)
                                    .type(SettingType.DEVICE)
                                    .required(true)
                                    .name("Select a device")
                                    .description("Tap to select")
                                )
                            )
                    }
                    "finish" -> {
                        response.page = Page()
                            .pageId("finish")
                            .nextPageId("intro")
                            .complete(true)
                            .name("Finish!")
                            .addSectionsItem(Section()
                                .addSettingsItem(SectionSetting()
                                    .type(SettingType.PARAGRAPH)
                                    .name("You are done!")
                                    .description("Description text")
                                    .defaultValue("This is information that we are displaying by default")
                                )
                            )
                    }
                }
                return Response.ok(response)
            }
        }
        return Response.notFound()
    }
}
