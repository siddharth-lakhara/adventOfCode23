const fs = require('fs');

const LIMITS = {
  red: 12,
  green: 13,
  blue: 14,
};

const getGamesData = (fileName) => {
    const data = fs.readFileSync(fileName, {
      encoding: "utf-8",
      flag: "r",
    });
    return data.split("\n").map((line) => {
       const gamesData = line.split(": ");
       return [+gamesData[0].split(" ")[1], gamesData[1]];
     });
}

const isValidSubGame = (subGame) => {
    const balls = subGame.split(", ");

    for (let idx=0; idx<balls.length; idx++) {
        const [n, color] = balls[idx].split(" ");
        if (+n > LIMITS[color]) {
          return false;
        }
    }
    return true;
}

const solveExample = () => {
    const gamesLines = getGamesData('./part1.txt');

    let sum = 0;
    gamesLines.forEach((gameLine) => {
      const gameId = gameLine[0];
      
      const subGame = gameLine[1].split('; ');
      let idx=0;
      for (; idx < subGame.length; idx++) {
        if (!isValidSubGame(subGame[idx])) {
            break;
        }
      }
      
      if (idx == subGame.length) {
        // possible
        sum += gameId;
      }
    });
    
    console.log(sum);
}

// finds max LoL
const calculateMinBalls = (subGame, globalMax) => {
    const balls = subGame.split(", ");

    balls.forEach((ball) => {
        const [n, color] = ball.split(" ");
        globalMax[color] = Math.max(globalMax[color], n);
    });
};

const cubes = (colors) => (colors.red * colors.green * colors.blue);

const solvePart2 = () => {
  const gamesLines = getGamesData("./part1.txt");

  let sum = 0;
  gamesLines.forEach((gameLine) => {
    const gameId = gameLine[0];

    const globalMax = {
      red: 0,
      green: 0,
      blue: 0,
    };
    const subGames = gameLine[1].split("; ");
    subGames.forEach((subGame) => {
        calculateMinBalls(subGame, globalMax);
    });

    sum += cubes(globalMax);
  });

  console.log(sum);
};

solvePart2();
