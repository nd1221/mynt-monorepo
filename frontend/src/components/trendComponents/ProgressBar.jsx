export default function ProgressBar({containerStyle, barStyle, progressDecimal}) {

    return (
        <div className={containerStyle}>
            <div
                className={barStyle}
                style={{width: `${progressDecimal * 100}%`}}
            ></div>
        </div>
    );
}