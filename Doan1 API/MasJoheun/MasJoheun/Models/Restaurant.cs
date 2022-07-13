using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class Restaurant
    {
        public Restaurant()
        {
            Events = new HashSet<Event>();
            Receipts = new HashSet<Receipt>();
        }

        public int Id { get; set; }
        public string Password { get; set; }
        public string Address { get; set; }
        public string Name { get; set; }
        public string Phone { get; set; }
        public int IdCity { get; set; }

        public virtual City IdCityNavigation { get; set; }
        public virtual ICollection<Event> Events { get; set; }
        public virtual ICollection<Receipt> Receipts { get; set; }
    }
}
