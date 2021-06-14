package de.espirit.firstspirit.modules.hackathon;

import com.espirit.moddev.components.annotations.ModuleComponent;
import de.espirit.common.base.Logger;
import de.espirit.common.base.Logging;
import de.espirit.firstspirit.agency.GlobalWebAppId;
import de.espirit.firstspirit.agency.ModuleAdminAgent;
import de.espirit.firstspirit.module.Module;
import de.espirit.firstspirit.module.ServerEnvironment;
import de.espirit.firstspirit.module.descriptor.ComponentDescriptor;
import de.espirit.firstspirit.module.descriptor.ModuleDescriptor;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

@ModuleComponent
public class HackathonIntegrationModule implements Module {

    private static final Logger LOGGER = Logging.getLogger();

    private ModuleDescriptor _moduleDescriptor;
    private ModuleAdminAgent _moduleAdminAgent;

    @Override
    public void init(@NotNull final ModuleDescriptor moduleDescriptor, @NotNull final ServerEnvironment serverEnvironment) {
        _moduleDescriptor = moduleDescriptor;
        _moduleAdminAgent = serverEnvironment.getBroker().requireSpecialist(ModuleAdminAgent.TYPE);
    }

    @Override
    public void installed() {
        addIntegrationToContentCreator();
    }


    private void addIntegrationToContentCreator() {
        final String webAppName = HackathonIntegrationWebComponent.NAME;
        final ComponentDescriptor webAppComponent = _moduleDescriptor.getComponentByName(webAppName);
        if (webAppComponent == null) {
            LOGGER.logInfo("Web-app component '" + webAppName + "' found for module '" + _moduleDescriptor
                    .getModuleName()
                    + "', so there will be no attempt to configure a global web application.",
                HackathonIntegrationModule.class);
            return;
        }

        final Collection<GlobalWebAppId> globalWebApps = _moduleAdminAgent.getGlobalWebApps(true);
        final Optional<GlobalWebAppId> contentCreatorWebApp = globalWebApps.stream().filter(
            globalWebAppId -> GlobalWebAppId.WEBEDIT.getGlobalId().equals(globalWebAppId.getGlobalId()))
            .findFirst();
        if (!contentCreatorWebApp.isPresent()) {
            LOGGER.logError("Global content creator web-app not found", HackathonIntegrationModule.class);
            return;
        }

        _moduleAdminAgent.installWebApp(_moduleDescriptor.getModuleName(), webAppComponent.getName(),
            contentCreatorWebApp.get());
    }

    private void autoStartService(@NotNull final String serviceName) {
        if (!_moduleAdminAgent.isAutostart(serviceName)) {
            LOGGER.logInfo("Enabling auto start for service '" + serviceName + "'...", getClass());
            _moduleAdminAgent.setAutostart(serviceName, true);
            LOGGER.logInfo("Auto start for service '" + serviceName + "' enabled.", getClass());
        }
    }

    @Override
    public void uninstalling() {
        // Uninstalling is only possible if web-app components have been removed manually.
        // Because of the risk of deleting the wrong global web-app, we do nothing.
    }

    @Override
    public void updated(final String version) {
        // do nothing
    }


}
