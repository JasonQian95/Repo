window.onload = function() {
    
	var startTime;
	var paused = false;
	var levelOne_hs = 0;
	var levelTwo_hs = 0;
    
    var level;
	var score = 0;
	var highScore = 0;
    
    // HTML element
	var levelForm = document.getElementById("levelForm");
	var startPage = document.getElementById("startPage");
	var gamePage = document.getElementById("gamePage");
	var gameOverPage = document.getElementById("gameOverPage");
    
    // Button element variables
	var startButton = document.getElementById("startButton");
	var restartButton = document.getElementById("restartButton");
	var pauseButton = document.getElementById("pauseButton");
	var exitButton = document.getElementById("exitButton");
    var levelOneButton = document.getElementById("levelOne");
	var levelTwoButton = document.getElementById("levelTwo");
    
    // view port and canvas
	var viewPort = document.getElementById("viewPort");
	var viewPortCanvas = document.getElementById("viewPortCanvas");
    var viewPortContext = viewPortCanvas.getContext("2d");
    var canvas_height = 600;
    
    // other elements
	var displayScore = document.getElementById("displayScore");
	var highScore = document.getElementById("highScore");
	var endScore = document.getElementById("endScore");
	var timeLeft = document.getElementById("timeLeft");
    
    var currentPage = 'startPage';
	var bugList = [];
	var foodList = [];
	var listKillBug = [];
	var createBugsSetTimeout;
	var updateObjectsSetInterval;
	var levelRadioButtonsList = levelForm.elements["radButton-level"];

	var key_level1HighScore = "highScoreKey1";
	var key_level2HighScore = "highScoreKey2";
	var gameTimeLength = 60;
    var remainingTime = gameTimeLength;
    var lastBugSpawn;    //Last bug that spawned in milliseconds
    
    //food and bug dimensions and spawn time.
	var bug_width = 10;
	var bug_height = 25;
	var food_width = 20;
	var food_height = 20;
	var bugRadius = 30;
    var maxFood = 5;
	var distOverlap = 20;
	var minTime_bugSpawn = 1000;   // min: 1 second
	var maxTime_bugSpawn = 3000;   // max: 3 second

	function startGame() {
		gameOverPage.style.display = 'none';
		resetAll();
		updateScore();
		resetRemainingTime();
		updateStartTime();
		updateRemainingTime();
        createFoods();
        createBugs();
        updateObjects();
	}
    
    var startAndExitButtonOnclick = function() {
		if (currentPage === 'startPage') {
			getLevel();
			if (typeof(level) === "undefined") {
				alert('You must select which level you want to play');
				return;
			}
			startPage.style.display = 'none';
			gamePage.style.display = 'block';
			currentPage = 'gamePage';
			viewPortCanvasClear();
			startGame();
		} 
		else {
			gameOverPage.style.display = 'none';
			startPage.style.display = 'block';
			gamePage.style.display = 'none';
			currentPage = 'startPage';
		}
	}

	function pause(){
	    if(!checkGameOver()){
			if(paused === true){
	        	updateStartTime();
		        updateRemainingTime();
		        createBugs();
		        updateObjects();
		        pauseButton.innerHTML = "Pause";
		        paused = false;
	        }
	        else{
		        window.clearInterval(createBugsSetTimeout);
		        window.clearInterval(updateObjectsSetInterval);
		        pauseButton.innerHTML = "Resume";
		        paused = true;
	        }   
	    }
	}

	function updateObjects(){
		updateObjectsSetInterval = setInterval(updateDraw, 1000/45);   // 45 frames per second
	}

	function updateDraw() {
		if(checkGameOver()) {
			gameOver();
			return;
		}
		viewPortCanvasClear();
        updateTime();
		drawBugs();
		drawFoods();
	}

	function gameOver(){
        window.clearInterval(createBugsSetTimeout);
        window.clearInterval(updateObjectsSetInterval);
        resetAll();
        getLevel();
        calcHighScore();
        setHighScore();
        gameOverAlert();
        resetScore();	
	}
    
	var viewPortCanvasClear = function() {
		viewPortContext.clearRect(0, 0, viewPortCanvas.width, viewPortCanvas.height);
	}

	var resetAll = function() {
		foodList = [];
		bugList = [];
		viewPortCanvasClear();
	}

	var getLevel = function() {
		for (var i = 0; i < levelRadioButtonsList.length; i++) {
			if(levelRadioButtonsList[i].checked){
				level = levelRadioButtonsList[i].value;
				break;
			}
		}
	}
	
	var gameOverAlert = function() {
		gameOverPage.style.display = 'block';
		endScore.innerHTML = "Your score is: " + score.toString();
	}

	function checkGameOver() {
        // either no more food or time
		if (foodList.length === 0 || Math.floor(remainingTime) <= 0) 
        {
			return true;
		}
		return false;
	}
    
    // func to delete either bug or food from their list.
	function deleteObject(objectToDelete, objectList) {
		var objIndex = objectList.indexOf(objectToDelete);
		if (objIndex >= 0) {
			objectList.splice(objIndex, 1);
		}
	}
	
	function calcHighScore(){
		if(level == 1 && score > levelOne_hs){
			levelOne_hs = score;
		}
		else if(score > levelTwo_hs){
			levelTwo_hs = score;
		}
	}
	
	function setHighScore(){
		getLevel();
		if (typeof(Storage) != "undefined") {
            // compare hs with local and the current one.
		    levelOne_hs = Math.max(localStorage.getItem(key_level1HighScore), levelOne_hs);
		    levelTwo_hs = Math.max(localStorage.getItem(key_level2HighScore), levelTwo_hs);
            // set the hs
		    localStorage.setItem(key_level1HighScore, levelOne_hs);
		    localStorage.setItem(key_level2HighScore, levelTwo_hs);
            
		    if(level == 1){
			    highScore.innerHTML = "High Score: " + localStorage.getItem(key_level1HighScore).toString();
		    } else{
			    highScore.innerHTML = "High Score: " + localStorage.getItem(key_level2HighScore).toString();
		    }
		} else {
			if(level == 1){
				highScore.innerHTML = "High Score: " + levelOne_hs.toString();
			} else{
				highScore.innerHTML = "High Score: " + levelTwo_hs.toString();
			}
		}
	}
	
	function resetScore(){
		score = 0;
	}

	function updateScore() {
		displayScore.innerHTML = "Score: " + score.toString();
	}

	function getDistance(x1, y1, x2, y2){
		var delta_x = x1 - x2;
		var delta_y = y1 - y2;
		return Math.sqrt(Math.pow((delta_x), 2) + Math.pow(delta_y, 2));
	}

	function findClosestFood(sx, sy){
		var tempFood;
		var distance;
		var closestFood;
		var minDistance;
		var closestDeltaX;
		var closestDeltaY;
		
        var i;
		for (i = 0; i < foodList.length; i++) {
			tempFood = foodList[i];
			distance = getDistance(tempFood.food_x, tempFood.food_y, sx, sy);
			if (minDistance > distance || typeof minDistance === "undefined") {
				closestFood = tempFood;
				minDistance = distance;
				closestDeltaX = tempFood.food_x - sx;
				closestDeltaY = tempFood.food_y - sy;
			} 
		}
		return {
			closestFood: closestFood,
			minDistance: minDistance,
			closestDeltaX: closestDeltaX,
			closestDeltaY: closestDeltaY
		};
	}
	
	function updateRemainingTime(){
		timeLeft.innerHTML = "Time Left: " + Math.floor(remainingTime).toString();
	}

	function updateStartTime() {
		startTime = new Date().getTime();
	}
	
	function updateTime(){
		var currentTime = new Date().getTime();
		var passedTime = currentTime - startTime;
        
		startTime = currentTime;
		remainingTime = remainingTime - passedTime/1000;
        
		if(Math.floor(remainingTime>0)){
			updateRemainingTime();	
		}
	}
	
	function resetRemainingTime(){
		remainingTime = gameTimeLength;
	}

	function drawBugs() {
        var i;
		for(i = bugList.length - 1; i >= 0; i--){
			drawBug(moveBug(bugList[i]));
		}
        //for killing bug from list
		for(i = listKillBug.length - 1; i >= 0; i--){
				deleteObject(listKillBug[i], listKillBug);
		}
	}
	
	function drawFoods(){
        var i;
		for(i = 0; i < foodList.length; i++){
			drawFood(foodList[i]);
		}
	}

	var createBugs = function() {  
        lastBugSpawn = lastBugSpawn || startTime;
		var temp = new Date().getTime() - lastBugSpawn;   //diff between new bug and last bug creation
        var interval = (minTime_bugSpawn + Math.random() * (maxTime_bugSpawn - minTime_bugSpawn)) - temp;
        
		createBugsSetTimeout =			
		setTimeout(
			function(){
				lastBugSpawn = new Date().getTime();    //set time of bug creation
				var bug_x = 10 + 380 * Math.random();   //width of bug, canvas width minus the bug width, multiplied with random
				var bug_y = 0;
				var bug = makeBug(bug_x, bug_y);
				bugList.push(bug);
				drawBug(bug);
				createBugs();
			}, interval);
	}
	
	var createFoods = function(){
		var food;
		var food_x;
		var food_y;  
		var distanceBetween = (viewPortCanvas.width - food_width * maxFood)/(maxFood + 1);
        var i;
		for (i = 0; i < maxFood; i++) {
			food_x = (i + 1) * distanceBetween + i * food_width;
			food_y = ((canvas_height - food_height/2) - (canvas_height / 2 + food_height/2))*Math.random() + (canvas_height / 2 + food_height/2);
			food = setFoodCoordinates(food_x, food_y);
			foodList.push(food); 
			drawFood(food);
		};
	}

	var bugObject = function(bug_x, bug_y, bugType, bugSpeed, bugScore) {
		var self = this;
		self.bug_x = bug_x;
		self.bug_y = bug_y;
		self.bugType = bugType;
		self.bugSpeed = bugSpeed;
		self.bugScore = bugScore;
		self.bugClosestFood;
		self.bugClosestFoodDistance;
		self.bugIncrementX;
		self.bugIncrementY;
		self.setClosestFood = function(){
			var theFood = findClosestFood(self.bug_x, self.bug_y);
			self.bugClosestFood = theFood.closestFood;
			self.bugClosestFoodDistance = theFood.minDistance;
			self.bugIncrementX = (((theFood.closestDeltaX)/theFood.minDistance)*self.bugSpeed)/45;
			self.bugIncrementY = (((theFood.closestDeltaY)/theFood.minDistance)*self.bugSpeed)/45;
		};
		self.getDistance = function(target_x, target_y) {
			var bug_x = self.bug_x;
			var bug_y = self.bug_y;
			
			if(( target_x < (self.bug_x + bug_width / 2)) && (target_x > (self.bug_x - bug_width / 2))){
				bug_x = target_x;
				if((target_y < (self.bug_y + bug_height / 2)) && (target_y > (self.bug_y - bug_height / 2))){
					bug_y = target_y;
				} else if(target_y > (self.bug_y + bug_height / 2)){
					bug_y = bug_y + bug_height / 2;
				} else if(target_y < (self.bug_y - bug_height / 2)){
					bug_y = bug_y - bug_height / 2;
				} 
			} else if((target_y < (self.bug_y + bug_height / 2)) && (target_y > (self.bug_y - bug_height / 2))){
				bug_y = target_y;
				if(target_x < (self.bug_x - bug_width / 2)){
					bug_x = bug_x - bug_width / 2;
				} else if(target_x > (self.bug_x + bug_width / 2)){
					bug_x = bug_x + bug_width / 2;
				}
			} else if((target_y > (self.bug_y + bug_height / 2)) && (target_x < (self.bug_x - bug_width / 2))){
				bug_x = bug_x - bug_width / 2;
				bug_y = bug_y + bug_height / 2;
			} else if((target_y > (self.bug_y + bug_height / 2)) && (target_x > (self.bug_x + bug_width / 2))){
				bug_x = bug_x + bug_width / 2;
				bug_y = bug_y + bug_height / 2;
			} else if((target_y < (self.bug_y - bug_height / 2)) && (target_x < (self.bug_x - bug_width / 2))){
				bug_x = bug_x - bug_width / 2;
				bug_y = bug_y - bug_height / 2;
			} else{
				bug_x = bug_x + bug_width / 2;
				bug_y = bug_y - bug_height / 2;
			}
			var delta_x = target_x - bug_x;
			var delta_y = target_y - bug_y;
			return Math.sqrt(Math.pow((delta_x), 2) + Math.pow(delta_y, 2));
		};
	}


	var makeBug = function(bug_x, bug_y) {
        var bugSpeed;
		var bugType;
        var bugScore;
        var list = [1, 1, 1, 2, 2, 2, 3, 3, 3, 3];
        var idx = Math.floor(Math.random() * list.length);
        
        if (list[idx] == 1){
			bugType = "black";
			bugScore = 5;
			if (level === 1) {
				bugSpeed = 150;
			} else {
				bugSpeed = 200;
			}
		} 
        else if(list[idx] == 2){
			bugType = "red";
			bugScore = 3;
			if (level === 1) {
				bugSpeed = 75;
			} else {
				bugSpeed = 100;
			}
		} else {
			bugType = "orange";
			bugScore = 1;
			if (level === 1) {
				bugSpeed = 60;
			} else {
				bugSpeed = 80;
			}
		}
		var bug = new bugObject(bug_x, bug_y, bugType, bugSpeed, bugScore);
		return bug;
	}
	
	var setFoodCoordinates = function(food_x, food_y) {
		var food = {
			food_x: food_x,
			food_y: food_y
		};
		return food;
	}
	
	function moveBug(bug){
		bug.setClosestFood();
		if(bug.bugClosestFoodDistance < distOverlap){
			deleteObject(bug.bugClosestFood, foodList);
		}
		else{
			bug.bug_x += bug.bugIncrementX;
			bug.bug_y += bug.bugIncrementY;
		}
		return bug;
	}
	
	function killBug(event){
		if(!paused){
			var bug;
			var rect = viewPortCanvas.getBoundingClientRect();
			var x_prime = rect.left;
			var y_prime = rect.top;
			var x = event.clientX - x_prime;
			var y = event.clientY - y_prime;
            
            var i;
			for(i = bugList.length - 1; i >= 0; i--){
				bug = bugList[i];
				if (bug.getDistance(x, y) <= bugRadius){
					score+=bug.bugScore;
					listKillBug.push(bug);
					deleteObject(bug, bugList);
					updateScore();
				}
			}	
		}
	}

	function drawBug(bugObject){
		viewPortContext.save();

		var x = bugObject.bug_x;
		var y = bugObject.bug_y;
		var color = bugObject.bugType;

        viewPortContext.fillStyle = color;
        viewPortContext.fillRect(x, y, 5, 5); //tail
        viewPortContext.fillRect(x - 2, y + 5, 9, 5); //1st body
        viewPortContext.fillRect(x, y + 10, 5, 5); //2nd body
        viewPortContext.fillRect(x - 2, y + 15, 9, 5); //3rd body
        viewPortContext.fillRect(x, y + 20, 5, 5); //head
        //antenna
        viewPortContext.moveTo(x, y + 25);
        viewPortContext.lineTo(x - 2, y + 27);
        viewPortContext.moveTo(x + 5, y + 25);
        viewPortContext.lineTo(x + 7, y + 27);
        viewPortContext.stroke();
        
		viewPortContext.restore();
	}
	
	function drawFood(food){
		var x = food.food_x;
		var y = food.food_y;
		viewPortContext.beginPath();
		viewPortContext.arc(x, y, 10, 0, 2*Math.PI);
		viewPortContext.fillStyle = "Green";
        viewPortContext.strokeStyle = "Black";
		viewPortContext.fill();
		viewPortContext.stroke();
	}
    
	startButton.onclick = startAndExitButtonOnclick;
	exitButton.onclick = startAndExitButtonOnclick;
	restartButton.onclick = startGame;
	pauseButton.onclick = pause;
	viewPortCanvas.onclick = killBug;
	levelOneButton.onclick = setHighScore;
	levelTwoButton.onclick = setHighScore;
	setHighScore();	
}