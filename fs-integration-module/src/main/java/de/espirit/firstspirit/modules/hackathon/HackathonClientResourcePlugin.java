package de.espirit.firstspirit.modules.hackathon;

import com.espirit.moddev.components.annotations.PublicComponent;
import de.espirit.firstspirit.access.BaseContext;
import de.espirit.firstspirit.webedit.plugin.ClientResourcePlugin;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@PublicComponent(name = "Hackathon ClientResource Plugin")
public class HackathonClientResourcePlugin implements ClientResourcePlugin {

    @Override
    public List<String> getScriptUrls() {
        return Collections.singletonList("fs-hackathon-cc-webapp/main.js");
    }

    @Override
    public List<String> getStylesheetUrls() {
        return Arrays.asList();
    }

    @Override
    public void setUp(final BaseContext baseContext) {

    }

    @Override
    public void tearDown() {

    }

}
