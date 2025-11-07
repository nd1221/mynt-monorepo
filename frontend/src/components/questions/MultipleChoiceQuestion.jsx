import { useState, useEffect } from "react";

export default function MultipleChoiceQuestion(props) {

    const {question, submitted, started = true, flagAnswer = () => {}, prevAnswer = null} = props;

    const [selectedChoice, setSelectedChoice] = useState(null);
    // Show user's answer if question submitted
    useEffect(() => {
        if (submitted && prevAnswer) {
            setSelectedChoice(prevAnswer);
        }
    }, [submitted, prevAnswer]);


    const handleSelect = choice  => {
        if (!submitted) {
            setSelectedChoice(choice);
            flagAnswer(question.id, choice.isCorrect, choice);
        }
    }

    const isChoiceSelected = choice => {
        return selectedChoice === choice;
    }

    const buildChoiceClassName = (choice, isSelected) => {
        
        let className = "text-md font-bold w-full text-white px-4 py-2 rounded-md border-3 ";
        
        if (submitted) {
            choice.isCorrect ? className += "bg-green-700/75" : className += "bg-red-700/75";
            if (isSelected) {
                className += " border-black";
            }
        } else {
            isSelected ? className += "bg-lavender" : className += "bg-slateBlue";
            className += " hover:opacity-85 active:scale-99 transition duration-50";
        }

        return className;
    }

    const getChoices = (choices) => {
        return choices.map(choice => {
            return (
                <button 
                    className={buildChoiceClassName(choice, isChoiceSelected(choice))}
                    disabled={submitted || !started}
                    onClick={() => {handleSelect(choice)}}
                >{choice.choiceText}</button> 
            );
        });
    }

    const getReviewMessage = () => {
        
        let review = "";

        if (selectedChoice) {
            selectedChoice.isCorrect ? review += "Correct - You answered: " : review += "Incorrect - You answered: ";
            return review += selectedChoice.choiceText;
        } else {
            return "No answer given";
        }
    }

    return (
        <>
            <p className="font-bold text-lg">{question.questionText}</p>
            <div className="flex flex-col flex-wrap gap-6 items-center min-w-4/5">
                <div className="flex flex-col w-full h-fit gap-4 items-center justify-center mb-2">
                    {getChoices(question.choices)}
                </div>
                {
                    submitted ?
                    <p className="text-md mt-4 font-bold">{getReviewMessage()}</p>
                    :
                    <p className="text-md mt-4 font-bold text-transparent">placeholder</p>
                }
            </div>
            <hr className="w-4/5 border-slateBlue/20"/>
        </>
    );
}