import { useTimer } from "react-timer-hook";

export default function Timer(props) {

    const {submitted, started, handleSubmit, handleStart, handleTimeExpired, timeLimit} = props;

    const expiryTimestamp = new Date();
    expiryTimestamp.setSeconds(expiryTimestamp.getSeconds() + timeLimit);

    const {
        seconds,
        minutes,
        hours,
        isRunning,
        start,
        pause,
        restart,
        totalSeconds,
    } = useTimer({
        expiryTimestamp: expiryTimestamp,
        autoStart: false,
        onExpire: handleTimeExpired
    });

    const toggleStart = () => {
        start();
        handleStart();
    }

    const toggleSubmit = () => {
        pause();
        handleSubmit(totalSeconds);
    }

    const formatTimeUnit = (timeUnit, showEmpty) => {
        
        if (timeUnit > 0) {
            if (timeUnit < 10) {
                return `0${timeUnit}`;
            }
            return timeUnit;
        }
        return showEmpty ? "00" : "";
    }

    const getTimeRemaining = () => {
        const h = formatTimeUnit(hours, false);
        const m = formatTimeUnit(minutes, true);
        const s = formatTimeUnit(seconds, true);
        const minsSecs = `${m} : ${s}`;
        return h ? `${h} : ${minsSecs}` : minsSecs;
    }

    return (
        <>
            <div className="flex flex-col h-20 w-fit mt-4 p-4 px-6 bg-slateBlue items-center justify-center rounded-xl shadow-xl">
                <p className="text-5xl mb-2 text-white font-bold">
                    {getTimeRemaining()}
                </p>
            </div>
            {
                started ?
                <button 
                    className={`text-xl font-bold w-full h-fit bg-tealDark/90 text-white px-8 py-4 rounded-lg shadow-xl ${submitted ? null : "hover:opacity-85 active:scale-98 transition duration-50"}`}
                    disabled={submitted || !started}
                    onClick={() => toggleSubmit()}
                >{submitted ? "submitted" : "submit"}</button>
                :
                <button 
                    className="text-xl font-bold w-full bg-tealDark/90 text-white px-8 py-4 rounded-lg shadow-xl hover:opacity-85 active:scale-98 transition duration-50"
                    disabled={submitted}
                    onClick={() => toggleStart()}
                >start</button>
            }
        </>
    );
}