var isSetup = true,
    placedShips = 0,
    game,
    vertical,
    ship,
    shipButtons = document.querySelectorAll('.ship-button button'),
    backdrop = document.querySelector('#modal-backdrop'),
    modal = document.querySelector("#modal"),
    startBanner = document.querySelector('#startBanner'),
    addShipBtn = document.querySelector('#addShipButton'),
    playerGrid,
    opponentGrid,
    oppStats = document.querySelectorAll('#opp-stats [data-title]');
    playerStats = document.querySelectorAll('#player-stats [data-title]');

    oppStats[1].innerText = 9;
    playerStats[1].innerText = 9;


var shipTypes = {
    'MINESWEEPER': {
        type: 'MINESWEEPER',
        length: 2
    },
    'DESTROYER': {
        type: 'DESTROYER',
        length: 3
    },
    'BATTLESHIP': {
        type: 'BATTLESHIP',
        length: 4
    }
};

var shipsLeft = {
    'MINESWEEPER': {
        'available': 1
    },
    'DESTROYER': {
        'available': 1
    },
    'BATTLESHIP': {
        'available': 1
    }
};

function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
    if (isPlayer) playerGrid = table;
    else opponentGrid = table;

}

function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS")
            className = "miss";
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK")
            className = "hit"
        else if (attack.result === "SURRENDER") {
            alert(surrenderText);
            location.reload();
        }
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);


    });
}

function redrawGrid() {
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}



function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        if(this.parentNode.parentNode.id == 'opponent') {
            alert('Place ships on your board only');
        }
        else if(shipsLeft[ship.type].available != 0){
            sendXhr("POST", "/place", {game: game, shipType: ship.type, x: row, y: col, isVertical: vertical}, function(data) {
                game = data;
                redrawGrid();
                placedShips++;
                (shipsLeft[ship.type].available)--;
                if (placedShips == 3) {
                    isSetup = false;
                    beginBanner();
                    registerCellListener((e) => {});
                    addShipBtn.classList.toggle('hidden');
                }
            });
        }
        else {
            alert("No more of that ship type");
        }
    }
    else if (this.parentNode.parentNode.id == 'opponent') {
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
        })
    }
    else {
        alert('Cannot attack your own board');
    }
}

function sendXhr(method, url, data, handler) {
    console.log("Player Stats", playerStats);
    console.log('Opponent Stats', oppStats);
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            alert("Cannot complete the action!!!!!!!");
            return;
        }
        handler(JSON.parse(req.responseText));
//        console.log(JSON.parse(req.responseText));
        updateStats(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function updateStats(data){
    var oppHits = playerHits = 0;

    oppStats[0].innerText = data.opponentsBoard.ships.length;
    playerStats[0].innerText = data.playersBoard.ships.length;

    for (var ship of data.opponentsBoard.ships) {
        for( var squares of ship.occupiedSquares){
            if (squares.hit == true)
                oppHits++;
        }
     }

     for (var ship of data.playersBoard.ships) {
             for( var squares of ship.occupiedSquares){
                 if (squares.hit == true)
                     playerHits++;
             }
          }

     oppStats[1].innerText = 9 - oppHits;
     oppStats[2].innerText = oppHits;
     playerStats[1].innerText = 9 - playerHits;
     playerStats[2].innerText = playerHits;

}





function place(ship) {
    return function() {
        let size = ship.length;
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function addShipModal(event){
     backdrop.classList.toggle('hidden');
     modal.classList.toggle('hidden');
}

function handleShipBtn(event){
     shipName = event.target.id.split("_")[1].toUpperCase();
     ship = shipTypes[shipName];
     registerCellListener(place(ship));
     addShipModal(event);
}

function beginBanner(){
    startBanner.classList.toggle('hidden');
    setTimeout(() => { startBanner.classList.toggle('hidden'); }, 3000);
}


function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    document.querySelector("#add_ship").addEventListener('click', addShipModal);
    document.querySelector('#modal-close-button').addEventListener('click', addShipModal);
    shipButtons.forEach((ship, idx, listobj) => {
        ship.addEventListener('click', handleShipBtn);
    });
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
};