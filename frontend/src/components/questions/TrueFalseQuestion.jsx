import { useState, useEffect } from "react";

export default function TrueFalseQuestion(props) {

    const {question, submitted, started = true, flagAnswer = () => {}, prevAnswer = null} = props;

    const [selectedChoice, setSelectedChoice] = useState(null);
    // Show user's answer if question submitted
    useEffect(() => {
        if (submitted && prevAnswer) {
            setSelectedChoice(prevAnswer);
        }
    }, [submitted, prevAnswer]);

    const handleSelect = choice => {
        if (!submitted) {
            setSelectedChoice(choice);
            flagAnswer(question.id, choice === question.correct, choice);
        }
    }

    const buildChoiceClassName = (booleanValue, isSelected) => {

        let className = "text-lg font-bold w-40 text-offWhite px-6 py-3 rounded-lg border-4 ";
        
        if (submitted) {
            question.correct === booleanValue ? className += "bg-green-700/75" : className += "bg-red-700/75";
            if (isSelected) {
                className += " border-black";
            }
        } else {
            isSelected ? className += "bg-lavender" : className += "bg-slateBlue";
            className += " hover:opacity-85 active:scale-98 transition duration-50";
        }

        return className;
    }
        
    const getChoices = selected => {

        return [true, false].map(bool => {
            return (
                <button 
                    className={buildChoiceClassName(bool, selected === bool)}
                    disabled={submitted || !started}
                    onClick={() => {handleSelect(bool)}}
                >{bool.toString()}</button>
            );
        });
    }
    
    const getReviewMessage = () => {
        
        let review = "";

        if (selectedChoice === true || selectedChoice === false) {
            selectedChoice === question.correct ? review += "Correct - You answered: " : review += "Incorrect - You answered: ";
            return review += selectedChoice.toString();
        } else {
            return "No answer given";
        }
    }

    return (
        <>
            <p className="font-bold text-lg">{question.questionText}</p>
            <div className="flex flex-col gap-6 items-center">
                <div className="flex flex-row w-fit h-fit gap-10 items-center justify-center mb-2">
                    {getChoices(selectedChoice)}
                </div>
                {
                    submitted ?
                    <p className="text-lg mt-4 font-bold">{getReviewMessage()}</p>
                    :
                    <p className="text-lg mt-4 font-bold text-transparent">placeholder</p>
                }
            </div>
            <hr className="w-4/5 border-slateBlue/20"/>
        </>
    );
}