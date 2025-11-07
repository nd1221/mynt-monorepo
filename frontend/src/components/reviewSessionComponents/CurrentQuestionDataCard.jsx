import { formatAverageTimeSeconds } from "../../utils/progressTrackingUtils";

export default function CurrentQuestionDataCard({data}) {

    return (
        <div className="col-span-2 flex flex-col w-full gap-4 items-center rounded-lg bg-slateBlue p-2 shadow-md">
            <div className="flex flex-col gap-2 border-1 border-teal rounded-md w-full h-full p-2 text-offWhite">
                <h1 className="italic font-bold text-teal">Current Question</h1>
                <hr className="border-1 border-teal/50 w-full" />
                <div className="flex flex-col w-full h-full">
                    <div className="flex flex-row justify-between">
                        <p className="italic text-sm">Answered:</p>
                        <p>{!data ? "--" : data.wasAnswered ? "✅" : "❌"}</p>
                    </div>
                    <div className="flex flex-row justify-between">
                        <p className="italic text-sm">Correct:</p>
                        <p>{!data ? "--" : data.wasCorrect ? "✅" : "❌"}</p>
                    </div>
                    <div className="flex flex-row justify-between">
                        <p className="italic text-sm">Time taken:</p>
                        <p className="italic font-semibold">{!data ? "--" : formatAverageTimeSeconds(data.timeTaken)}</p>
                    </div>
                    <div className="flex flex-row justify-between">
                        <p className="italic text-sm">Your user rating:</p>
                        <p className="italic font-semibold">{!data ? "--" : data.userRating}</p>
                    </div>
                </div>
            </div>
        </div>
    );
}