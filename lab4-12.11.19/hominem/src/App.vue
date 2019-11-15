<template>
  <div id="app">
      <Header/>
      <div id="contentContainer" class="shaded">
          <Validator/>
      </div>
      <Footer/>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Validator from "@/components/Validator";
import Footer from "@/components/Footer";

export default {
  name: 'app',
  components: {Footer, Validator, Header},
  methods: {
      keyMapper: function (keystrokeDelay, keySequence, callback) {
          let buffer = [];
          let lastKeyTime = Date.now();
          document.addEventListener("keydown", event => {
              let charList = "abcdefghijklmnopqrstuvwxyz0123456789";
              const key = event.key.toLowerCase();
              if (charList.indexOf(key) === -1) return;
              let currentTime = Date.now();
              if (currentTime - lastKeyTime > 1000) buffer = [];
              buffer.push(key);
              lastKeyTime = currentTime;
              if (buffer.join("") === keySequence) callback();
          })
      }
  },
  created: function () {
      this.keyMapper(1000, "joke", function () {
          document.querySelectorAll("*").forEach((node) => node.classList.add("rotated"));
          const parent = document.getElementById("content");
          while (parent.firstChild) parent.firstChild.remove();
          const img = document.createElement("img");
          img.src = "/assets/img/joker.jpg";
          parent.appendChild(img);
      });
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
        height: 100%;
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