import { formatAverageTimeSeconds } from "../../utils/progressTrackingUtils";

export default function CurrentSessionDataCard({data}) {

    return (
        <div className="col-span-3 flex flex-col w-full gap-4 items-center rounded-lg bg-slateBlue p-2 shadow-md">
            <div className="flex flex-col gap-2 border-1 border-lavender rounded-md w-full h-full p-2 text-offWhite">
                <h1 className="italic font-bold text-lavender">Session Tracker</h1>
                <hr className="border-1 border-lavender/50 w-full" />
                <div className="grid grid-cols-5 grid-rows-3 gap-4 w-full h-full">
                    <p className="col-span-2 text-sm">🔥 streak: <span className="italic font-semibold text-md">{data.streak}</span></p>
                    <p className="col-span-3 text-sm">🏆 longest streak: <span className="italic font-semibold text-md">{data.longestStreak}</span></p>
                    <p className="col-span-2 text-sm">✅ correct: <span className="italic font-semibold text-md">{data.correct}</span></p>
                    <p className="col-span-3 text-sm">📚 total: <span className="italic font-semibold text-md">{data.totalSeen}</span></p>
                    <p className="col-span-2 text-sm">⏭️ skipped: <span className="italic font-semibold text-md">{data.skipped}</span></p>
                    <p className="col-span-3 text-sm">⏱️ time: <span className="italic font-semibold text-md">{formatAverageTimeSeconds(data.totalTimeTaken)}</span></p>
                </div>
            </div>
        </div>
    );
}