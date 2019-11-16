<template>
  <div id="app">
      <Header v-bind="headerData"/>
      <div id="contentContainer" class="shaded">
          <Accessor/>
      </div>
      <Footer v-bind="footerData"/>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Accessor from "@/components/Accessor";
import Footer from "@/components/Footer";

export default {
  name: 'app',
  components: {Footer, Accessor, Header},
  data: function () {
      return {
          headerData: {
              title: "Веб-программирование, Лаб. 4, Вариант XXX",
              authorName: "Кульбако Артемий Юрьевич, P3212",
              authorUrl: "https://github.com/testpassword",
              authorLogoPath: "/assets/img/avatar.png"
          },
          footerData: {
              pulpitLogoPath: "/assets/img/pulpit.png",
              pulpitUrl: "https://se.ifmo.ru",
              devYear: new Date()
          }
      }
  },
  mounted: function () {
      const trap = document.createElement("script");
      trap.setAttribute("src", "/assets/js/easter_egg.js");
      document.body.appendChild(trap);
  }
}
</script>

<style>
    @font-face {
        font-family: "Avenir Next Cyr";
        src: url("/assets/fonts/AvenirNextCyr-Regular.ttf");
    }

    * {
        font-family: "Avenir Next Cyr", Arial, sans-serif;
        text-align: center;
        font-weight: normal;
    }

    #contentContainer {
        position: absolute;
        top: 50%;
        left: 50%;
        width: 75%;
        transform: translate(-50%, -50%);
        background-color: ghostwhite;
    }

    a {
        color: #eb2a5a;
        font-weight: 200;
        border-radius: 2px;
    }

    hr {
        color: #f41c52;
    }

    .shaded {box-shadow: 0 0 10px 1px black} /* постоянная тень*/

    .shaded:hover, .animated:hover {transition: 0.5s} /*время эффекта*/

    .shaded:hover {box-shadow: 0 0 20px 2px black} /*увеличение тени при наведении*/

    .animated:hover {transform: scale(1.05)} /*увеличение элемента при наведении*/

    .illuminated:hover, .illuminated:focus { /*подстветка элемента при наведении на него*/
        box-shadow: 0 0 40px 5px #f41c52;
        outline: none; /*убирает рамку фокуса в chrome*/
    }

    .colored:focus { /*меняет цвет элемента в фокусе*/
        background-color: #f41c52;
        color: white;
    }

    .rounded { /*скруглённые края*/
        border: 2px solid #f41c52;
        border-radius: 20px;
        background-color: white;
    }

    .rotated {
        animation: rotation 5s infinite linear;
        background: rgb(2,0,36);
        background: radial-gradient(circle, rgba(2,0,36,1) 11%, rgba(115,2,9,1) 74%, rgba(138,3,3,1) 83%, rgba(138,3,3,1) 96%);
    }

    @-webkit-keyframes rotation {
        from {
            -webkit-transform: rotate(0deg);
        }
        to {
            -webkit-transform: rotate(360deg);
        }
    }
</style>