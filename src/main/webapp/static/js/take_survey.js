
//todo: doesn't load
$(document).ready(function () {

    var numQuestions = $('.questionRow').length;
    var choicePercent = (100 / numQuestions).toFixed(2);
    var progressionPercent = 0;
    var answeredQuestions = [];

    $('.progress-text').text(progressionPercent + '%');

    //update progress bar on choice selection
    $('.radioChoice').on('change', function () {
        if ($.inArray(this.name, answeredQuestions) === -1) {
            progressionPercent += +(choicePercent.replace('%', ''));

            //todo: assumes page has less than 50 questions (adjust this later if paging is added)
            if (progressionPercent > 99.5) {
                progressionPercent = 100;
            }

            $('.progress-bar').css({'width': progressionPercent + '%'});
            $('.progress-text').text(progressionPercent + '%');
            answeredQuestions.push(this.name);

            console.log("after update progressionPercent: " + progressionPercent);
            console.log("width now: " + $('.progress-bar').css('width'));
        }
//            else {
//                console.log("already answered question");
//            }
    });

});