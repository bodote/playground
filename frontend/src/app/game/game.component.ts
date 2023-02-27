import {AfterViewInit, Component, ElementRef, HostListener, ViewChild} from '@angular/core';
import { Observable, Subscription, interval } from 'rxjs';


@Component({
  selector: 'snake-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent  implements AfterViewInit {

  @ViewChild('canvas', {static: true})
  canvas: ElementRef<HTMLCanvasElement>|null = null;

  private context: CanvasRenderingContext2D|undefined|null = undefined;
  private width: number=300;
  private height: number=300;
  private scale: number = 10;

  private snake: Array<[number, number]> = [];
  private food: [number, number] = [0, 0];
  private direction: [number, number] = [1, 0];

  private gameLoop$: Observable<number>|undefined;
  private subscription: Subscription|undefined;
  private gameOver: boolean=false;

  ngAfterViewInit() {
    if (this.canvas==undefined) throw new Error("error");
    this.context = this.canvas.nativeElement.getContext('2d');
    this.width = this.canvas.nativeElement.width;
    this.height = this.canvas.nativeElement.height;


    // Initialize the snake
    this.snake.push([
      Math.floor(this.width / 2 / this.scale) * this.scale,
      Math.floor(this.height / 2 / this.scale) * this.scale
    ]);

    // Initialize the snake with 20 blocks
    // const x = Math.floor(this.width / 2 / this.scale) * this.scale;
    // const y = Math.floor(this.height / 2 / this.scale) * this.scale;
    // for (let i = 0; i < 20; i++) {
    //   this.snake.push([x - i * this.scale, y]);
    // }

    // Generate the initial food location
    this.generateFood();

    // Create the game loop
    this.gameLoop$ = interval(100);
    this.subscription = this.gameLoop$.subscribe(() => this.update());
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  private update() {
    // Move the snake
    const [x, y] = this.snake[0];
    const [dx, dy] = this.direction;
    const newX = (x + dx * this.scale + this.width) % this.width;
    const newY = (y + dy * this.scale + this.height) % this.height;

    // Check for collision with the food
    if (newX === this.food[0] && newY === this.food[1]) {
      this.snake.unshift(this.food);
      this.generateFood();
    } else {
      this.snake.pop();
      this.snake.unshift([newX, newY]);
    }

    // Check for collision with the snake's body
    if (this.snake.slice(1).some(([x, y]) => x === newX && y === newY)) {
      this.gameOver = true;
      this.subscription?.unsubscribe();
    }

    // Draw the game
    this.draw();
  }

  private draw() {
    if (this.context==undefined) throw new Error("error");
    // Clear the canvas
    this.context.clearRect(0, 0, this.width, this.height);

    if (this.gameOver) {
      // Draw the "game over" text in the center of the canvas
      this.context.font = '30px Arial';
      this.context.fillStyle = 'red';
      this.context.textAlign = 'center';
      this.context.fillText('Game Over', this.width / 2, this.height / 2);

      // Draw the "Start New Game" button in the center of the canvas
      this.context.fillStyle = 'green';
      this.context.fillRect(this.width / 2 - 100, this.height / 2 + 30, 200, 50);
      this.context.font = '20px Arial';
      this.context.fillStyle = 'white';
      this.context.textAlign = 'center';
      this.context.fillText('Start New Game', this.width / 2, this.height / 2 + 60);


    } else {
      // Draw the snake
      this.context.fillStyle = 'green';
      for (const [x, y] of this.snake) {
        this.context.fillRect(x, y, this.scale, this.scale);
      }
    }

    // Draw the food
    this.context.fillStyle = 'red';
    this.context.fillRect(this.food[0], this.food[1], this.scale, this.scale);
  }

  private generateFood() {
    const x = Math.floor(Math.random() * (this.width / this.scale)) * this.scale;
    const y = Math.floor(Math.random() * (this.height / this.scale)) * this.scale;
    this.food = [x, y];
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent) {
    const key = event.key;
    switch (key) {
      case 'ArrowUp':
        if (this.direction[1] !== 1) {
          this.direction = [0, -1];
        }
        break;
      case 'ArrowDown':
        if (this.direction[1] !== -1) {
          this.direction = [0, 1];
        }
        break;
      case 'ArrowLeft':
        if (this.direction[0] !== 1) {
          this.direction = [-1, 0];
        }
        break;
      case 'ArrowRight':
        if (this.direction[0] !== -1) {
          this.direction = [1, 0];
        }
        break;
    }
  }
  @HostListener('click', ['$event'])
  handleClick(event: MouseEvent) {
    if (this.gameOver) {
      const x = event.offsetX;
      const y = event.offsetY;
      if (x >= this.width / 2 - 100 && x <= this.width / 2 + 100 && y >= this.height / 2 + 30 && y <= this.height / 2 + 80) {
        // Restart the game
        this.snake = [];
        this.snake.push([
          Math.floor(this.width / 2 / this.scale) * this.scale,
          Math.floor(this.height / 2 / this.scale) * this.scale
        ]);

        // Stop the current subscription and start a new one
        this.subscription?.unsubscribe();
        this.gameLoop$ = interval(100);
        this.subscription = this.gameLoop$.subscribe(() => this.update());

        this.direction = [1, 0];
        this.gameOver = false;
        this.generateFood();
      }
    }
  }

}
