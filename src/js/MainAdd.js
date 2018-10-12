var name;
var IPs = [];

function show(div) {
    var element = document.getElementById(div);
    element.classList.toggle("showContent");
    document.getElementById('CP2').style.height = $('#CP1').outerHeight();
}

function textSwapper(text, tag) {

    var x = document.getElementsByTagName(tag);
    var i;
    for (i = 0; i < x.length; i++) {
        x[i].textContent = text;
    }
}

var getIPAddress = function () {
    $.getJSON("https://jsonip.com?callback=?", function (data) {
        if (!IPs.includes(data.ip)) {
            IPs.push(data.ip);
            console.log(IPs);
        }
    });
};

window.onload = function () {
    input = "";
    if (!localStorage.getItem("DontShow")) {
        document.getElementById('modal').style.display = "block";
    }

    document.getElementById('modalbutton').onclick = function () {
        localStorage.setItem("DontShow", "true");
        document.getElementById('modal').style.display = "none";
    };
    document.getElementById('modalbuttonX').onclick = function () {
        document.getElementById('modal').style.display = "none";
        getIPAddress()

    };


};
var right = document.getElementById('CP2').style.height;
var left = document.getElementById('CP1').style.height;

$(document).keypress(function (event) {
    input = input.concat(String.fromCharCode(event.which));
    if (input == "a") {
        document.getElementById('CP2').style.height = $('#CP1').outerHeight();
        console.log($('#CP1').height());
        console.log($('#CP2').height());
        input = "";
    }
});

$(window).resize(function () {
    document.getElementById('CP2').style.height = $('#CP1').outerHeight();
});
document.getElementById('CP2').style.height = $('#CP1').height();
