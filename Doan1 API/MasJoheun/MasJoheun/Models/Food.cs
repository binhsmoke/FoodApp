using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class Food
    {
        public Food()
        {
            ComboDetails = new HashSet<ComboDetail>();
            Favourites = new HashSet<Favourite>();
            ReceiptDetails = new HashSet<ReceiptDetail>();
        }

        public int Id { get; set; }
        public string NameFood { get; set; }
        public int PriceFood { get; set; }
        public string Image { get; set; }
        public string IsAvailable { get; set; }
        public int IdType { get; set; }
        public int OrderTime { get; set; }
        public string Ingredients { get; set; }

        public virtual FoodType IdTypeNavigation { get; set; }
        public virtual ICollection<ComboDetail> ComboDetails { get; set; }
        public virtual ICollection<Favourite> Favourites { get; set; }
        public virtual ICollection<ReceiptDetail> ReceiptDetails { get; set; }
    }
}
