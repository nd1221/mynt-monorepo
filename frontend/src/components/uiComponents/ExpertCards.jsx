import anonIcon from "../../assets/anon-user.png";

export default function ExpertCards({experts}) {

    return experts.map(expert => (
        <div className="flex flex-row h-20 min-w-50 w-fit p-2 justify-left items-center gap-4 bg-white border-1 border-slateBlue/10 rounded-lg shadow-lg hover:shadow-md">
            <img className="h-12 opacity-50 ml-2" src={anonIcon} alt="Expert profile picture" />
            <p className="text-lg font-bold mr-4 text-slateBlue">{expert}</p>
        </div>
    ));
}