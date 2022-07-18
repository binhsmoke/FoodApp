using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class City
    {
        public City()
        {
            Restaurants = new HashSet<Restaurant>();
        }

        public int Id { get; set; }
        public string Name { get; set; }

        public virtual ICollection<Restaurant> Restaurants { get; set; }
    }
}
