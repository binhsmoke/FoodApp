using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class Combo
    {
        public Combo()
        {
            ComboDetails = new HashSet<ComboDetail>();
        }

        public int Id { get; set; }
        public string NameCombo { get; set; }
        public int DiscountForCombo { get; set; }
        public string Image { get; set; }
        public string IsAvailable { get; set; }

        public virtual ICollection<ComboDetail> ComboDetails { get; set; }
    }
}
