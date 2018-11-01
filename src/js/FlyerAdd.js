var name;
var IPs = [];

function show(div) {
    var element = document.getElementById(div);
    element.classList.toggle("showContent");
   
}

function textSwapper(text, tag) {

    var x = document.getElementsByTagName(tag);
    var i;
    for (i = 0; i < x.length; i++) {
        x[i].textContent = text;
    }
}

var right = document.getElementById('CP2').style.height;
var left = document.getElementById('CP1').style.height;


$(window).resize(function () {
    document.getElementById('CP2').style.height = $('#CP1').outerHeight();
});

$(document).ready(function() {
    document.getElementById('CP2').style.height = $('#CP1').height();

});