using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class ComboDetail
    {
        public int IdFood { get; set; }
        public int IdCombo { get; set; }

        public virtual Combo IdComboNavigation { get; set; }
        public virtual Food IdFoodNavigation { get; set; }
    }
}
