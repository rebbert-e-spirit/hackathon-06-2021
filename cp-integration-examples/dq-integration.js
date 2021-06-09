// ==UserScript==
// @name         Button in DQ
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://cms.crownpeak.net/*
// @icon         https://www.google.com/s2/favicons?domain=crownpeak.com
// @grant        none
// ==/UserScript==

(function() {
    'use strict';



    const script = document.createElement('script')
    script.src = 'http://localhost:8080/cxt-platform/microapps/api.js'
    document.head.append(script)

   async function initNavigation(){
     const navigation = document.querySelector('nav.dashboard-nav');

     const buttons = await CxtMicroApps.getButtons({});
        for(let btn of buttons){
            const ul = document.createElement('ul');
            ul.classList.add('nav-menu','dqm-nav-menu','primary-nav')
            ul.style="background-color: #1a6c92; padding: 5px;"

            const li = document.createElement('li');
            li.setAttribute('data-menu-name', 'adminMenu');
            li.innerHTML = `<span href="#" class="hover-dash" style="cursor: pointer;">${btn.title}</span>`
            li.addEventListener('click', ()=>btn.action())

            ul.append(li)
            navigation.append(ul);
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


})();
