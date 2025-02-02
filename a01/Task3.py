def bracket_validation(s):
    brackets = {'}': '{', ']': '[', ')': '('}
    stack = []
    
    for character in s:
        if character in brackets.values():
            stack.append(character)
        elif character in brackets.keys():
            if not stack or stack.pop() != brackets[character]:
                return False
    return not stack