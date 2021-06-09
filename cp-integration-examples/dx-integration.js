// ==UserScript==
// @name         Button in DX
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://cms2.crownpeak.net/*
// @icon         https://www.google.com/s2/favicons?domain=crownpeak.com
// @grant        none
// ==/UserScript==

(function() {
    'use strict';



    const script = document.createElement('script')
    script.src = 'http://localhost:8080/cxt-platform/microapps/api.js'
    document.head.append(script)

   async function initNavigation(){
     const navigation = document.querySelector('cp-sidebar');

     const buttons = await CxtMicroApps.getButtons({});
        for(let btn of buttons){
            console.log('btn', btn)
            const button = document.createElement('button');
            button.classList.add('module','dashboard')
            button.style="";
            button.title=btn.title


            const divider = document.createElement('div');
            divider.classList.add('divider');
            button.append(divider)

            const icon = document.createElement('div');
            icon.classList.add('icon');
            icon.style="padding: 5px;"
            button.append(icon);

            const img = document.createElement('img')
            img.src = btn.iconUrl;
            icon.append(img)





            button.addEventListener('click', ()=>btn.action())

           
            navigation.append(button);
        }

    }

    function wait(){
        if(window.CxtMicroApps){
            console.log('MicroApps are ready')
            initNavigation()
        }else{
           setTimeout(()=>wait(), 300)
        }

    }
    wait()


})()
