var x = 5;
function upscroller() {
  if(x>0){
    x = x - 5; 
  }
  window.scrollTo(0, x);
  console.log(x);
}
function downscroller() {
  if(x<1000){
    x = x + 5;   
  }
  
  window.scrollTo(0, x);
  console.log(x);
}
$(".ups").on({
    mousemove: function () {
          upscroller();
          console.log("ayyy!");
    },
    mouseleave: function () {
        //stuff to do on mouse leave
    }
});
$(".downs").on({
    mousemove: function () {
        downscroller();
        console.log("yaaa!");
        console.log(x);
    },
    mouseleave: function () {
        //stuff to do on mouse leave
    }
});

