import sys

current_year = None
total = 0

for line in sys.stdin:
    year, value = line.strip().split(",")
    value = int(float(value))

    if current_year != year:
        if current_year is not None:
            print(f"({current_year}, {total})")

        current_year = year
        total = 0

    total += value

print(f"({current_year}, {total})")
