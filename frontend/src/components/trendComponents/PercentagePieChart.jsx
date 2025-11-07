import { PieChart, Pie, Legend, Cell, ResponsiveContainer, Tooltip } from 'recharts';
import { formatDecimal } from '../../utils/progressTrackingUtils';

export default function PercentagePieChart({percentage}) {

    const colours = ['#A78BFA', '#1F2D3D'];

    const data = [
        {name: "percentage", value: percentage},
        {name: "non-percentage", value: (100 - percentage)}
    ];

    const getLegend = () => {
        return (
            <div className="flex flex-col items-center w-full h-fit font-bold">
                <h1 className="text-slateBlue/50 text-xl">Avg. Score</h1>
                <p className="text-lavender text-4xl">{formatDecimal(percentage, 1)} %</p>
            </div>
        );        
    };

    return (
        <div className="flex flex-col items-center justify-center w-50 h-60">
            <ResponsiveContainer width="100%" height="100%">
                <PieChart width={800} height={400}>
                    <Pie
                        data={data}
                        cx="50%"
                        cy="50%"
                        innerRadius={35}
                        outerRadius={60}
                        fill="#8884d8"
                        paddingAngle={5}
                        dataKey="value"
                    >
                        {data.map((_, index) => (
                            <Cell key={`cell-${index}`} fill={colours[index]} />
                        ))}
                    </Pie>
                    <Legend content={getLegend} />
                </PieChart>
            </ResponsiveContainer>
        </div>
    );
}