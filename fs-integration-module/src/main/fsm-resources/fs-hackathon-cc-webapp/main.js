(function () {

    //const script = document.createElement('script');
    //script.src = 'http://localhost:8080/cxt-platform/microapps/api.js';
    //document.head.append(script);

    async function initNavigation(){
        let toolbarContainer = document.querySelector('cc-client').shadowRoot.querySelector('#fs-toolbar').querySelector('.fs-toolbar-viewmodes');

        let htmlString =`<div class="fs-toolbar-viewmode" title="Hackathon messaging MicroApp">
                            <div class="fs-toolbar-viewmode-icon" style="-webkit-mask: url('fs-hackathon-cc-webapp/icons/translate.svg') center center no-repeat;">
                            </div>
                         </div>`
        let div = document.createElement('div');
        div.innerHTML = htmlString.trim();

        // Change this to div.childNodes to support multiple top-level nodes
        let button = div.firstChild;

        button.addEventListener('click', ()=>CxtMicroApps.requestMicroApp("cp-hackathon-micro-app-demo", {}));

        toolbarContainer.append(button);
        console.log('button added');


    }

    function waitForCxtAndInsertHackathonMicroApp(currentTimeout) {
        let maxWaitTime = 3 * 60 * 1000;    // 3 minutes seconds
        let waitTime = 5 * 1000;            // 5 seconds
        setTimeout(() => {
            // wait for CxtMicroApps to be connected
            if (!top.CxtMicroApps || !CxtMicroApps.mainConnection.connected) {
                if (currentTimeout < maxWaitTime) {
                    waitForCxtAndInsertHackathonMicroApp(currentTimeout + waitTime);
                } else {
                    console.error("Error registering 'fs-hackathon-microapp', CxtMicroApps seems to be unavailable!")
                }
                return;
            }


            console.log('now registring microapp');
            initNavigation();
        }, waitTime);
    }



    waitForCxtAndInsertHackathonMicroApp(0);

})();

