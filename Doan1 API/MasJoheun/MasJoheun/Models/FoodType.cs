using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class FoodType
    {
        public FoodType()
        {
            Foods = new HashSet<Food>();
        }

        public int Id { get; set; }
        public string NameType { get; set; }
        public string Image { get; set; }

        public virtual ICollection<Food> Foods { get; set; }
    }
}
