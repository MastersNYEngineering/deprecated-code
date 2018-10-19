var name;

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