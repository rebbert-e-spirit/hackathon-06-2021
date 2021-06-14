package de.espirit.firstspirit.modules.hackathon;

import com.espirit.moddev.components.annotations.WebAppComponent;
import com.espirit.moddev.components.annotations.WebResource;
import de.espirit.firstspirit.module.WebApp;
import de.espirit.firstspirit.module.WebEnvironment;
import de.espirit.firstspirit.module.descriptor.WebAppDescriptor;

@WebAppComponent(name = "fs-hackathon-cc-webapp",
    displayName = "Hackathon MicroApp Integration",
    description = "Integration WebappComponent for Hackathon",
    webXml = "web.xml",
    webResources = {
            @WebResource(path = "fs-hackathon-cc-webapp/", name = "de.espirit.firstspirit.modules.hackathon:web-resource", version = "$project.version")
    })
public class HackathonIntegrationWebComponent implements WebApp {

    public static final String NAME = "fs-hackathon-cc-webapp";  // change this name if you change the name of the web component

    @Override
    public void createWar() {

    }

    @Override
    public void init(final WebAppDescriptor webAppDescriptor, final WebEnvironment webEnvironment) {

    }

    @Override
    public void installed() {

    }

    @Override
    public void uninstalling() {

    }

    @Override
    public void updated(final String s) {

    }

}
