import Handlebars from "handlebars"
import { Component } from 'react'
import './App.css'

var oldHref = "http://localhost:3000"

interface Cell {
  text: String;
  clazz: String;
  link: String;
}

interface Cells {
  cells: Array<Cell>,
  template: HandlebarsTemplateDelegate<any>,
  instructions: String
  godCards: String
}

interface Props {
}

class App extends Component<Props, Cells> {
  constructor(props: Props) {
    super(props);
    this.state = {
      cells: [
        { text: "", clazz: "playable", link: "/play?x=0&y=0" },
        { text: "", clazz: "playable", link: "/play?x=1&y=0" },
        { text: "", clazz: "playable", link: "/play?x=2&y=0" },
        { text: "", clazz: "playable", link: "/play?x=3&y=0" },
        { text: "", clazz: "playable", link: "/play?x=4&y=0" },
        { text: "", clazz: "playable", link: "/play?x=0&y=1" },
        { text: "", clazz: "playable", link: "/play?x=1&y=1" },
        { text: "", clazz: "playable", link: "/play?x=2&y=1" },
        { text: "", clazz: "playable", link: "/play?x=3&y=1" },
        { text: "", clazz: "playable", link: "/play?x=4&y=1" },
        { text: "", clazz: "playable", link: "/play?x=0&y=2" },
        { text: "", clazz: "playable", link: "/play?x=1&y=2" },
        { text: "", clazz: "playable", link: "/play?x=2&y=2" },
        { text: "", clazz: "playable", link: "/play?x=3&y=2" },
        { text: "", clazz: "playable", link: "/play?x=4&y=2" },
        { text: "", clazz: "playable", link: "/play?x=0&y=3" },
        { text: "", clazz: "playable", link: "/play?x=1&y=3" },
        { text: "", clazz: "playable", link: "/play?x=2&y=3" },
        { text: "", clazz: "playable", link: "/play?x=3&y=3" },
        { text: "", clazz: "playable", link: "/play?x=4&y=3" },
        { text: "", clazz: "playable", link: "/play?x=0&y=4" },
        { text: "", clazz: "playable", link: "/play?x=1&y=4" },
        { text: "", clazz: "playable", link: "/play?x=2&y=4" },
        { text: "", clazz: "playable", link: "/play?x=3&y=4" },
        { text: "", clazz: "playable", link: "/play?x=4&y=4" },
      ],
      template: this.loadTemplate(),
      instructions: "Turn: Player A, ",
      godCards: "Player A God Card: N/A, Player B God Card: N/A"
    };
    
  }

  loadTemplate (): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  convertToCell(p: any): Array<Cell> {
    const newCells: Array<Cell> = [];
    for (var i = 0; i < p["cells"].length; i++) {
      var c: Cell = {
        text: p["cells"][i]["text"],
        clazz: p["cells"][i]["clazz"],
        link: p["cells"][i]["link"],
      };
      newCells.push(c);
    }

    return newCells;
  }

  getGodCardA(p: any) : String {
    return p["godCardA"]
  }

  getGodCardB(p: any) : String {
    return p["godCardB"]
  }

  getTurn(p: any): String {
    return p["turn"]
  }

  getWinner(p: any): String | undefined {
    return p["winner"]
  }

  getInstr(turn: String, winner: String | undefined) {
    if (winner === undefined) return "Turn: Player " + turn + ", "
    else return "Player " + winner + " wins!"
  }

  async newGame() {
    const response = await fetch("newgame");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    this.setState({ cells: newCells, instructions: "Turn: Player A, ", godCards: "Player A God Card: N/A , Player B God Card: N/A" })
  }

  async none() {
    const response = await fetch("none");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const godCardA = this.getGodCardA(json)
    const godCardB = this.getGodCardB(json)
    const gods = "Player A God Card: " + godCardA +  ", Player B God Card: " + godCardB 
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, godCards: gods})
  }

  async demeterCard() {
    const response = await fetch("demeter");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const godCardA = this.getGodCardA(json)
    const godCardB = this.getGodCardB(json)
    const gods = "Player A God Card: " + godCardA +  ", Player B God Card: " + godCardB 
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, godCards: gods})
  }

  async minotaurCard() {
    const response = await fetch("minotaur");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const godCardA = this.getGodCardA(json)
    const godCardB = this.getGodCardB(json)
    const gods = "Player A God Card: " + godCardA +  ", Player B God Card: " + godCardB 
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, godCards: gods})
  }
  async panCard() {
    const response = await fetch("pan");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const godCardA = this.getGodCardA(json)
    const godCardB = this.getGodCardB(json)
    const gods = "Player A God Card: " + godCardA +  ", Player B God Card: " + godCardB 
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, godCards: gods})
  }

  async apolloCard() {
    const response = await fetch("apollo");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const godCardA = this.getGodCardA(json)
    const godCardB = this.getGodCardB(json)
    const gods = "Player A God Card: " + godCardA +  ", Player B God Card: " + godCardB 
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, godCards: gods})
  }

  async hephaestusCard() {
    const response = await fetch("hephaestus");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const godCardA = this.getGodCardA(json)
    const godCardB = this.getGodCardB(json)
    const gods = "Player A God Card: " + godCardA +  ", Player B God Card: " + godCardB 
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, godCards: gods})
  }

  async play(url: String) {
    const href = "play?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    console.log(newCells)
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const godCardA = this.getGodCardA(json)
    const godCardB = this.getGodCardB(json)
    const gods = "Player A God Card: " + godCardA +  ", Player B God Card: " + godCardB 
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, godCards: gods})
  }

  async pass() {
    const response = await fetch("pass");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const godCardA = this.getGodCardA(json)
    const godCardB = this.getGodCardB(json)
    const gods = "Player A God Card: " + godCardA +  ", Player B God Card: " + godCardB 
    const instr = this.getInstr(turn, winner)
    this.setState({ cells: newCells, instructions: instr, godCards: gods})
  }

  async switch() {
    if (
      window.location.href === "http://localhost:3000/newgame" &&
      oldHref !== window.location.href
    ) {
      this.newGame();
      oldHref = window.location.href;
    } else if (
      window.location.href.split("?")[0] === "http://localhost:3000/play" &&
      oldHref !== window.location.href
    ) {
      this.play(window.location.href);
      oldHref = window.location.href;
    } 
    else if (
      window.location.href === "http://localhost:3000/none" &&
      oldHref !== window.location.href
    ) {
      this.none();
      oldHref = window.location.href;
    }
    else if (
      window.location.href === "http://localhost:3000/demeter" &&
      oldHref !== window.location.href
    ) {
      this.demeterCard();
      oldHref = window.location.href;
    }
    else if (
      window.location.href === "http://localhost:3000/minotaur" &&
      oldHref !== window.location.href
    ) {
      this.minotaurCard();
      oldHref = window.location.href;
    }
    else if (
      window.location.href === "http://localhost:3000/pan" &&
      oldHref !== window.location.href
    ) {
      this.panCard();
      oldHref = window.location.href;
    }
    else if (
      window.location.href === "http://localhost:3000/apollo" &&
      oldHref !== window.location.href
    ) {
      this.apolloCard();
      oldHref = window.location.href;
    }
    else if (
      window.location.href === "http://localhost:3000/hephaestus" &&
      oldHref !== window.location.href
    ) {
      this.hephaestusCard();
      oldHref = window.location.href;
    }
    else if (
      window.location.href === "http://localhost:3000/pass" &&
      oldHref !== window.location.href
    ) {
      this.pass();
      oldHref = window.location.href;
    }
  };

  render() {
    this.switch()
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ cells: this.state.cells, instructions: this.state.instructions, godCards: this.state.godCards}),
          }}
        />
      </div>
    )
  };
};

export default App;