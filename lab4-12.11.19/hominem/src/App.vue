<template>
  <div id="app">
      <Header :title="title" :author-name="authorName" :author-url="authorUrl" :author-logo="authorLogo"/>
      <Validator :content-title="contentTitle"/>
      <Footer :pulpit-logo="pulpitLogo" :pulput-url="pulpitUrl" :dev-year="devYear"/>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Validator from "@/components/Validator";
import Footer from "@/components/Footer";

export default {
  name: 'app',
  components: {
      Footer, Validator, Header
  },
  data: function () {
      return {
          title: "Веб-программирование, Лаб. 3, Вариант XXX",
          authorName: "Кульбако Артемий Юрьевич, P3212",
          authorUrl: "https://github.com/testpassword",
          authorLogo: "/assets/img/avatar.png",
          pulpitLogo: "/assets/img/pulpit.png",
          pulpitUrl: "https://se.ifmo.ru",
          devYear: "2019",
          contentTitle: "Валидация введёных значений"
      }
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

    a {
        color: #eb2a5a;
        font-weight: 200;
        border-radius: 2px;
    }

    a:hover {box-shadow: 0 0 40px 5px #f41c52}

    body {background-color: whitesmoke}

    input, a {cursor: pointer}

    .shaded {box-shadow: 0 0 10px 1px black}

    .shaded:hover, .animated:hover {transition: 0.5s}

    .shaded:hover {box-shadow: 0 0 20px 2px black}

    .animated:hover {transform: scale(1.05)}

    .illuminated:hover, .illuminated:focus {
        box-shadow: 0 0 40px 5px #f41c52;
        outline: none; /*убирает рамку фокуса в chrome*/
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
