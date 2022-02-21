# WORDLE with suggestions <img src="https://user-images.githubusercontent.com/57832437/155004616-06701a8f-71a4-42a0-9c73-e890fe6a7690.png" width="30" height="30" />

### Overview:
- University assignment for CS211.
- Assignment is worth 3%.
- Additional task is worth an extra 3%.
- A clip of the final version can be seen [here](https://user-images.githubusercontent.com/57832437/155008633-87d676a4-85cd-4020-8505-c57ecfb179d7.mp4).

### Task:
- Create a program identical to [WORDLE](https://www.nytimes.com/games/wordle/index.html).
- Use only Java (SWING / AWT), no Java FX.

### Additional Task:
- Create suggestions for the user based off their input.

<br>

## Task 1 Result:

![image](https://user-images.githubusercontent.com/57832437/155003030-a195771a-bee8-4c39-8d25-c46ac147beeb.png)

- I wanted it to be identical as possible to Wordle. - Which was tough as swing is ancient 🙂
- Used same font (before NYT takeover), color palette and also the same words that wordle has as possible answers, which differs from words they accept.
- Program has a help window and replay function and is errorfree (As user's input is limited, just like Wordle).

<br>

## Task 2 Result:

- I added an additional text field that displays up to 3 suggestions for the user, based off of their input.
- To test this, the user can type ***'XXXXX'*** which will run a function that inputs ***'ALIEN'*** and ***'TOURS'*** and then it will enter the 1st suggestion that is based off the results of the first 2 inputs.
- The ***'XXXXX'*** function will solve the word before the attempts run out 100% of the time. 💯
- A clip of this can be seen [here](https://user-images.githubusercontent.com/57832437/155007958-4b2e5836-cdc9-46de-b49e-2137aab153b0.mp4).

![image](https://user-images.githubusercontent.com/57832437/155006342-41ae8a36-b329-4a71-9f08-201d86b37f40.png)
