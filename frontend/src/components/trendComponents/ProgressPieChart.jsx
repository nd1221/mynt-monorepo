import { PieChart, Pie, Legend, Cell, ResponsiveContainer, Tooltip } from 'recharts';

export default function ProgressPieChart({totalCount, correctCount, dimensions = "lg:h-60 lg:w-50 xl:h-60 xl:w-50", textDimensions="text-xl"}) {
    
    const colours = ['#A78BFA', '#1F2D3D'];
    const placeholderColour = ['#1f2d3d40'];

    const data = [
        {name: "correct", value: correctCount},
        {name: "incorrect", value: totalCount - correctCount}
    ];

    const getPlaceholderData = () => {
        return [
            {name: "not attempted", value: 1}
        ];
    }

    const getPercentage = count => {
        const percentage = (count / totalCount) * 100;
        const precision = percentage.toString().split(".")[0].length + 1;
        return percentage.toPrecision(precision);
    };

    const getLegend = () => {
        return (
            <ul className={`flex flex-col ${textDimensions} items-center mt-2 h-fit w-full`}>
                <div className="flex flex-row w-full justify-between font-bold text-lavender">
                    <li>correct</li>
                    {
                        totalCount === 0 ?
                        "--"
                        :
                        <li>{getPercentage(correctCount)}%</li>
                    }
                </div>
                <div className="flex flex-row w-full justify-between font-bold text-slateBlue">
                    <li>incorrect</li>
                    {
                        totalCount === 0 ?
                        "--"
                        :
                        <li>{getPercentage(totalCount - correctCount)}%</li>
                    }
                </div>
            </ul>
        );        
    };

    return (<div className={`flex flex-col items-center justify-center ${dimensions}`}>
            <ResponsiveContainer width="100%" height="100%">
                <PieChart width={800} height={400}>
                    <Pie
                        data={totalCount === 0 ? getPlaceholderData() : data}
                        cx="50%"
                        cy="50%"
                        innerRadius={35}
                        outerRadius={60}
                        fill="#8884d8"
                        paddingAngle={5}
                        dataKey="value"
                    >
                        {data.map((_, index) => (
                            <Cell key={`cell-${index}`} fill={totalCount === 0 ? placeholderColour[index] : colours[index]} />
                        ))}
                    </Pie>
                    <Legend content={getLegend} />
                    <Tooltip />
                </PieChart>
            </ResponsiveContainer>
        </div>
        
    );
}