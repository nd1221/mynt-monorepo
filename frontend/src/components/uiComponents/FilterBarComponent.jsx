import downArrow from "../../assets/expand-arrow.png";

export default function FilterBarComponent(props) {

    const {filter, handleChange, toggleFilterBar} = props;

    return (
        <div className="flex flex-col gap-2">
            <button onClick={() => toggleFilterBar(filter.id)} className="flex flex-row items-center justify-between bg-slateBlue text-offWhite font-bold text-xl text-start p-2 border-b-2 border-offWhite/90 cursor-pointer hover:text-lavender">
                {filter.category}
                <img className="h-6" src={downArrow} alt="Expand filters icon" />
            </button>
            {filter.on ?
                <div className="w-full h-fit bg-slateBlue p-4">
                    {
                        filter.options.map(option => (
                            <label className="flex flex-row gap-4 items-center cursor-pointer">
                                <input
                                    className="appearance-none w-4 h-4 border-1 border-offWhite rounded-sm bg-slateBlue checked:bg-lavenderDark"
                                    type={filter.category === "From" ? "radio" : "checkbox"}
                                    onChange={handleChange}
                                    key={option.key}
                                    name={option.key}
                                    checked={option.checked}
                                    data-parent-id={filter.id}
                                />
                                <span className="text-offWhite text-xl truncate">{option.key}</span>
                            </label>
                        ))
                    }
                </div>
                : null
            }
        </div>
    );
}