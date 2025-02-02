# Task 1
def count_above_average(array):
    if not array:
        return 0
    average = sum(array)/len(array)
    count = sum(1 for number in array if number > average)
    return count