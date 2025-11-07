import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { formatDate } from '../../utils/progressTrackingUtils';

export default function Graph({data, fillColour, domain = null, ticks = null}) {
    
    const hasNoData = data.length === 0 || data == null;

    return (
        <div className="w-9/10 lg:h-48 xl:h-48 my-6">
            <ResponsiveContainer width="100%" height="100%">
                <AreaChart data={data} >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" tickMargin={12} tickFormatter={formatDate} />
                    <YAxis domain={domain} ticks={ticks} tickMargin={10} />
                    <Tooltip labelFormatter={formatDate} formatter={(value, name) => [`${value} s`, name]} />
                    <Area
                        type="monotone"
                        dataKey="value"
                        stroke="#A78BFA"
                        strokeWidth={2}
                        fillOpacity={0.50}
                        fill={fillColour}
                        dot={{stroke: "#A78BFA", strokeWidth: 1, r: 3, strokeDasharray: ""}}
                    />  
                </AreaChart>
            </ResponsiveContainer>
            {
                hasNoData ?
                <p className="flex items-center justify-center text-slateBlue/35 italic">
                    no data found
                </p>
                : null
            }
        </div>
    );
}